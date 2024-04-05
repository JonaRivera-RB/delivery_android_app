import com.example.delivery.BuildConfig
import com.example.delivery.data.api.Endpoints
import com.example.delivery.data.api.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService {

    class Builder {
        private lateinit var retrofit: Retrofit
        private val httpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        fun getApi(): Endpoints = retrofit.create(Endpoints::class.java)

        fun getRetrofit(): Builder {
            setup()
            return this
        }

        private fun setup() {
            val client = getClient()
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        private fun getClient(): OkHttpClient =
            httpBuilder
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(requestDebugging())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()

        private fun requestDebugging(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            return logging
        }
    }
}
