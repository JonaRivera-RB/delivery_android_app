package com.example.delivery.remove_code.routes.reprecated

import com.example.delivery.data.models.api.routes.UserRoutes

class ApiRoutes {

    private val apiUrl = "http://192.168.1.64:3000/api/"
    private val retrofit = RetrofitClient()

    fun getUsersRoutes(): UserRoutes {
        return retrofit.getClient(apiUrl).create(UserRoutes::class.java)
    }
}