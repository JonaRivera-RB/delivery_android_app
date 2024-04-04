package com.example.delivery.data.models.api.Providers

import com.example.delivery.remove_code.routes.reprecated.ApiRoutes
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.data.models.User
import com.example.delivery.data.models.api.routes.UserRoutes
import retrofit2.Call

class UsersProvider {

    private var usersRoutes: UserRoutes? = null

    init {
        val api = ApiRoutes()
        usersRoutes = api.getUsersRoutes()
    }

    fun register(user: User): Call<ResponseHttp>? {
        return usersRoutes?.register(user)
    }

    fun login(email: String, password: String): Call<ResponseHttp>? {
        return usersRoutes?.login(email, password)
    }
}