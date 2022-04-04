import com.tech.mcity.data.model.CityResponse
import com.tech.mcity.data.remote.Api
import retrofit2.Response
import java.io.IOException

class FakeApi: Api {

    var city: CityResponse? = null

    var failureMsg: String? = null


    fun addCity(cityResponse: CityResponse){

        city = cityResponse
    }

    fun clearCity(){

        city = null
    }


    override suspend fun getCity(
        page: String?,
        filter: String?,
        include: String
    ): CityResponse {

        failureMsg?.let {
            throw IOException(it)
        }

        return city!!

    }

}