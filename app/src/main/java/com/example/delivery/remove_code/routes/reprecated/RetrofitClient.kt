package com.example.delivery.remove_code.routes.reprecated

import retrofit2.Retrofit

class RetrofitClient {

    fun getClient(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            //.addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}