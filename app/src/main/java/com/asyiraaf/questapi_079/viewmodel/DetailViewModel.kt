package com.asyiraaf.questapi_079.viewmodel

import RepositoryDataSiswa
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asyiraaf.questapi_079.modeldata.DataSiswa
import com.asyiraaf.questapi_079.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Sesuaikan nama state dengan PDF
sealed interface StatusUIDetail {
    data class Success(val satuSiswa: DataSiswa) : StatusUIDetail
    object Error : StatusUIDetail
    object Loading : StatusUIDetail
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    private val _idSiswa: Int = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    // Gunakan nama variabel sesuai PDF: statusUIDetail
    var statusUIDetail: StatusUIDetail by mutableStateOf(StatusUIDetail.Loading)
        private set

    init {
        getSatuSiswa()
    }

    // Gunakan nama fungsi sesuai PDF: getSatuSiswa
    fun getSatuSiswa() {
        viewModelScope.launch {
            statusUIDetail = StatusUIDetail.Loading
            statusUIDetail = try {
                val siswa = repositoryDataSiswa.getSatuSiswa(_idSiswa)
                StatusUIDetail.Success(siswa)
            } catch (e: IOException) {
                StatusUIDetail.Error
            } catch (e: HttpException) {
                StatusUIDetail.Error
            }
        }
    }

    // Gunakan nama fungsi sesuai PDF: hapusSatuSiswa
    suspend fun hapusSatuSiswa() {
        repositoryDataSiswa.hapusSatuSiswa(_idSiswa)
    }
}