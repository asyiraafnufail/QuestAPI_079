package com.asyiraaf.questapi_079.viewmodel

import com.asyiraaf.questapi_079.modeldata.DataSiswa

sealed interface StatusUISiswa{
    data class Success(val siswa: List<DataSiswa>) : StatusUISiswa
    object Error : StatusUISiswa
    object Loading : StatusUISiswa
}
