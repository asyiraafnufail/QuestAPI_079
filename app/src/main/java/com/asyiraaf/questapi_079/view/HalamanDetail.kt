package com.asyiraaf.questapi_079.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.asyiraaf.questapi_079.R
import com.asyiraaf.questapi_079.modeldata.DataSiswa
import com.asyiraaf.questapi_079.uicontroller.route.DestinasiDetail
import com.asyiraaf.questapi_079.viewmodel.DetailViewModel
import com.asyiraaf.questapi_079.viewmodel.StatusUIDetail // Pastikan ini diimport sesuai PDF
import com.asyiraaf.questapi_079.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSiswaScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiDetail.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Perbaikan: Menggunakan statusUIDetail sesuai PDF
                    val state = viewModel.statusUIDetail
                    if (state is StatusUIDetail.Success) {
                        navigateToEditItem(state.satuSiswa.id) // PDF menggunakan satuSiswa
                    }
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_siswa)
                )
            }
        }
    ) { innerPadding ->
        // Perbaikan: Menggunakan statusUIDetail dan fungsi yang sesuai PDF
        DetailBody(
            detailUiState = viewModel.statusUIDetail,
            retryAction = { viewModel.getSatuSiswa() }, // PDF: getSatuSiswa
            onDelete = {
                coroutineScope.launch {
                    viewModel.hapusSatuSiswa() // PDF: hapusSatuSiswa
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
}

@Composable
fun DetailBody(
    detailUiState: StatusUIDetail, // Tipe data disesuaikan dengan PDF
    retryAction: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailUiState) {
        is StatusUIDetail.Loading -> LoadingScreen(modifier = modifier)
        is StatusUIDetail.Error -> ErrorScreen(retryAction = retryAction, modifier = modifier)
        is StatusUIDetail.Success -> {
            Column(
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
            ) {
                // PDF menggunakan variabel 'satuSiswa'
                ItemDetailSiswa(
                    siswa = detailUiState.satuSiswa,
                    modifier = Modifier.fillMaxWidth()
                )

                var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.delete))
                }

                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDelete()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemDetailSiswa(
    siswa: DataSiswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            ComponentDetailSiswa(judul = stringResource(R.string.nama), isinya = siswa.nama)
            ComponentDetailSiswa(judul = stringResource(R.string.alamat), isinya = siswa.alamat)
            ComponentDetailSiswa(judul = stringResource(R.string.telpon), isinya = siswa.telpon)
        }
    }
}

@Composable
fun ComponentDetailSiswa(
    judul: String,
    isinya: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ) {
        Text(
            text = judul,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.tanya)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes))
            }
        }
    )
}