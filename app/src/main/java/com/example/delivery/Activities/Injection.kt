package com.example.delivery.Activities

import android.content.Context
import com.example.delivery.Activities.Login.LoginData.LoginRemote
import com.example.delivery.Activities.Login.LoginData.LoginRepository
import com.example.delivery.Activities.register.data.RegisterRemote
import com.example.delivery.Activities.register.data.RegisterRepository

object Injection {

    fun loginRepository(context: Context): LoginRepository {
        return LoginRepository.getInstance(LoginRemote(context))
    }

    fun registerRepository(context: Context): RegisterRepository {
        return RegisterRepository.getInstance(RegisterRemote(context))
    }
}