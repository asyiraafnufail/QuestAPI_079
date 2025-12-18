package com.asyiraaf.questapi_079.apiservice

import com.asyiraaf.questapi_079.modeldata.DataSiswa
import retrofit2.http.GET

interface ServiceApiSiswa {
    @GET("bacaTeman.php")
    suspend fun getSiswa(): List<DataSiswa>
}

