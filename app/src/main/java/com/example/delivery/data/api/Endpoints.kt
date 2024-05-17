package com.example.delivery.data.api

import com.example.delivery.Activities.Login.Entities.LoginModel
import com.example.delivery.Activities.profiles.Client.address.create.data.AddressModel
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.entities.Product
import com.example.delivery.Activities.profiles.Restaurant.home.entities.Category
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.Activities.register.entities.User
import okhttp3.Address
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

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
    @Multipart
    @POST("categories/create")
    fun createCategory(
        @Part("category") category: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<ResponseHttp>

    @GET("categories/getAll")
    fun getAll(): Call<ArrayList<Category>>

    @Multipart
    @POST("products/create")
    fun createProduct(
        @Part images: Array<MultipartBody.Part?>,
        @Part ("product") product: RequestBody,
    ): Call<ResponseHttp>

    @GET("products/findByCategory/{id_category}")
    fun getAllProductsByCategory(@Path("id_category") idCategory: String): Call<ArrayList<Product>>

    @POST("address/create")
    fun createAddress(
        @Body address: AddressModel
    ): Call<ResponseHttp>
}