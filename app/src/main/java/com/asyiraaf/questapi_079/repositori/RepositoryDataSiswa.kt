import com.asyiraaf.questapi_079.apiservice.ServiceApiSiswa
import com.asyiraaf.questapi_079.modeldata.DataSiswa

interface RepositoryDataSiswa{
    suspend fun getSiswa(): List<DataSiswa>
    suspend fun  postDatasiswa(dataSiswa: DataSiswa): retrofit2.Response<Void>
}

