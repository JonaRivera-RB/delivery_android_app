package com.example.delivery.data.api

import android.content.Context
import com.example.delivery.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService {

    class Builder {
        private lateinit var retrofit: Retrofit
        private lateinit var httpBuilder: OkHttpClient.Builder

        fun getApi(): Endpoints = retrofit.create(Endpoints::class.java)

        fun addHeaders(context: Context?): Builder {
            httpBuilder = OkHttpClient.Builder()
            requestDebugging()
            interceptors(context)
            setup()
            return this
        }

        private fun setup() {
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient())
                .build()
        }

        private fun httpClient(): OkHttpClient {
            return httpBuilder
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        private fun getGsonBuilder(): Gson =
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

        private fun requestDebugging() {
            val logging = HttpLoggingInterceptor()

            httpBuilder.addInterceptor(logging)

            if (BuildConfig.DEBUG)
                logging.level = HttpLoggingInterceptor.Level.BODY
            else
                logging.level = HttpLoggingInterceptor.Level.NONE
        }

        private fun interceptors(context: Context?) {
            //val tokenAuthenticator= TokenAuthenticator(context)

            val jwt = if (context != null) {
                ""//SessionManager.getInstance(context).getToken()
            } else ""

            val interceptor = Interceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    //.addHeader("apikey", BuildConfig.API_KEY)
                    .addHeader("Content-Type", "application/json")

                if (jwt.isNotEmpty()) {
                    request.addHeader("Authorization", "Bearer $jwt")
                }

                request.method(original.method(), original.body())

                chain.proceed(request.build())
            }

            //httpBuilder.authenticator(tokenAuthenticator)
            httpBuilder.addInterceptor(interceptor)
        }
    }
}