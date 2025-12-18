package com.asyiraaf.questapi_079.modeldata

@Serializable
data class DataSiswa(
    val id: Int,
    val nama: String,
    val alamat: String,
    val telpon: String
)

data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false
)
