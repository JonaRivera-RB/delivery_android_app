package com.example.delivery.Activities.Login.LoginData

import com.example.delivery.data.models.User

class LoginRepository (
    private val loginRemote: LoginDataSource
): LoginDataSource {

    override fun login(email: String, password: String, callback: LoginDataSource.LoginCallback) {
     loginRemote.login(email, password, object : LoginDataSource.LoginCallback{
         override fun success(user: User) {
             callback.success(user)
         }

         override fun error(errorMessage: String) {
             callback.error(errorMessage)
         }
     })
    }

    companion object {
        private var INSTANCE: LoginRepository ?= null

        fun getInstance(
            remoteLogin: LoginDataSource
        ): LoginRepository {
            return INSTANCE ?: LoginRepository(
                remoteLogin
            ).apply {
                INSTANCE = this
            }
        }
    }
}