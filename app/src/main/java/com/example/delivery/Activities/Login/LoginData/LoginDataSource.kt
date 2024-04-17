package com.example.delivery.Activities.Login.LoginData

import com.example.delivery.Activities.register.entities.User


interface LoginDataSource {

    interface LoginCallback {
        fun success(user: User)
        fun error(errorMessage: String)
    }

    fun login(email: String, password: String, callback: LoginCallback)
}