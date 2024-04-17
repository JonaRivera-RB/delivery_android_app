import android.content.Context
import com.example.delivery.BuildConfig
import com.example.delivery.data.api.Endpoints
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.utils.SessionManager
import okhttp3.Interceptor
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

        fun getRetrofit(context: Context?): Builder {
            interceptors(context)
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
                .addInterceptor(requestDebugging())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()

        private fun requestDebugging(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            return logging
        }
        private fun interceptors(context: Context?) {

            val jwt = if (context != null) {
                SessionManager.getInstance(context).getDataFromPreferences("user", User::class.java)?.session_token
            } else ""

            val interceptor = Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .addHeader("Content-Type", "application/json")

                if (jwt?.isNotEmpty() == true) {
                    request.addHeader("Authorization", jwt)
                }

                chain.proceed(request.build())
            }

            httpBuilder.addInterceptor(interceptor)
        }
    }
}
