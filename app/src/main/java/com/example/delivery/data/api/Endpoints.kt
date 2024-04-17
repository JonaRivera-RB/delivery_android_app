package com.example.delivery.data.api

import com.example.delivery.Activities.Login.Entities.LoginModel
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.Activities.register.entities.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface Endpoints {

    @POST("users/create")
    fun register(@Body user: User): Call<ResponseHttp>

    @POST("users/login")
    fun login(@Body body: LoginModel): Call<ResponseHttp>

    @Multipart
    @PUT("users/update")
    fun update(
        @Part("user") user: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<ResponseHttp>
    @PUT("users/updateWithoutImage")
    fun updateWithoutImage(@Body user: User): Call<ResponseHttp>
}