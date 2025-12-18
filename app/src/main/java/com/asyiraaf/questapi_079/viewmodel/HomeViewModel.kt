package com.asyiraaf.questapi_079.viewmodel

import RepositoryDataSiswa
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asyiraaf.questapi_079.modeldata.DataSiswa
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface StatusUISiswa{
    data class Success(val siswa: List<DataSiswa>) : StatusUISiswa
    object Error : StatusUISiswa
    object Loading : StatusUISiswa
}

class HomeViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel(){
    var listSiswa: StatusUISiswa by mutableStateOf(StatusUISiswa.Loading)
        private set

    init{
        loadSiswa()
    }

    fun loadSiswa(){
        viewModelScope.launch {
            listSiswa = StatusUISiswa.Loading
            listSiswa = try{
                StatusUISiswa.Success(repositoryDataSiswa
                    .getDataSiswa())
            }catch (e: Exception){
                StatusUISiswa.Error
            }
            catch (e: HttpException){
                StatusUISiswa.Error
            }
        }
    }
}