package com.example.delivery.Activities.register.data

import com.example.delivery.data.models.User

interface RegisterDataSource {

    interface RegisterCallback {
        fun success(message: String, id: String)
        fun error(error: String)
    }

    fun register(user: User, callback: RegisterCallback)
}