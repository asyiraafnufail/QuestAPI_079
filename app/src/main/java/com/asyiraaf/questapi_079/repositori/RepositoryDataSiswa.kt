import com.asyiraaf.questapi_079.apiservice.ServiceApiSiswa
import com.asyiraaf.questapi_079.modeldata.DataSiswa
import retrofit2.Response

interface RepositoryDataSiswa{
    suspend fun getSiswa(): List<DataSiswa>
    suspend fun  postDatasiswa(dataSiswa: DataSiswa): retrofit2.Response<Void>
}

class JaringanRepositoryDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa
): RepositoryDataSiswa {
    override suspend fun getSiswa(): List<DataSiswa> = serviceApiSiswa.getSiswa()
    override suspend fun postDatasiswa(dataSiswa: DataSiswa): Response<Void> = serviceApiSiswa.postSiswa(dataSiswa)
}