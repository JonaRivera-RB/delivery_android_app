package com.example.delivery.data.api

import com.example.delivery.Activities.Login.Entities.LoginModel
import com.example.delivery.Activities.profiles.Client.address.create.data.AddressModel
import com.example.delivery.Activities.profiles.Client.orders.models.Order
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.entities.Product
import com.example.delivery.Activities.profiles.Restaurant.home.entities.Category
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.data.models.NewResponseHttp
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
    fun login(@Body body: LoginModel): Call<NewResponseHttp>

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

    @GET("address/findByUser/{id_user}")
    fun getAllAddressByUser(
        @Path("id_user") idUser: String
    ): Call<ArrayList<AddressModel>>

    @POST("orders/create")
    fun createOrder(
        @Body order: Order
    ): Call<ResponseHttp>

    @GET("orders/findByStatus/{status}")
    fun getOrdersByStatus(
        @Path("status") status: String
    ): Call<ArrayList<Order>>

    @GET("orders/findByClientAndStatus/{id_client}/{status}")
    fun getOrdersByClientAndStatus(
        @Path("id_client") idClient: String,
        @Path("status") status: String
    ): Call<ArrayList<Order>>

    @PUT("orders/updateToDispatched")
    fun updateToDispatchedOrder(
        @Body order: Order
    ): Call<NewResponseHttp>

    @GET("users/findDeliveryMen")
    fun getDeliveryMen(): Call<ArrayList<User>>

    @GET("orders/findByDeliveryAndStatus/{id_delivery}/{status}")
    fun getOrdersByDeliveryAndStatus(
        @Path("id_delivery") idDelivery: String,
        @Path("status") status: String
    ): Call<ArrayList<Order>>
    @PUT("orders/updateToOnTheWay")
    fun updateToOnTheWay(
        @Body order: Order
    ): Call<NewResponseHttp>
}