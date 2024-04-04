package com.example.delivery.data.api

import com.example.delivery.Activities.Login.Entities.LoginModel
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.data.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Endpoints {

    @POST("users/create")
    fun register(@Body user: User): Call<ResponseHttp>

    @POST("users/login")
    fun login(@Body body: LoginModel): Call<ResponseHttp>
}