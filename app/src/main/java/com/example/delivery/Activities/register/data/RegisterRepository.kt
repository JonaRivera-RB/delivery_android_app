package com.example.delivery.Activities.register.data

import com.example.delivery.Activities.register.entities.User

class RegisterRepository(
    private val registerDataSource: RegisterDataSource
): RegisterDataSource {

    override fun register(user: User, callback: RegisterDataSource.RegisterCallback) {
        registerDataSource.register(user, object : RegisterDataSource.RegisterCallback {
            override fun success(message: String, id: String) {
                callback.success(message, id)
            }

            override fun error(error: String) {
                callback.error(error)
            }
        })
    }

    companion object {
        private var INSTANCE: RegisterRepository?= null

        fun getInstance(
            registerRemote: RegisterDataSource
        ): RegisterRepository {
            return INSTANCE ?: RegisterRepository(
                registerRemote
            ).apply {
                INSTANCE = this
            }
        }
    }
}