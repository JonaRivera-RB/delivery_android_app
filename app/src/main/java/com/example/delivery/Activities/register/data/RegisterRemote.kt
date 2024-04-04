package com.example.delivery.Activities.register.data

import android.content.Context
import com.example.delivery.Activities.Login.LoginData.LoginDataSource
import com.example.delivery.data.api.RetrofitService
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.data.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRemote(private val context: Context): RegisterDataSource {

    override fun register(user: User, callback: RegisterDataSource.RegisterCallback) {
        val call = RetrofitService.Builder()
            .addHeaders(context)
            .getApi()
            .register(user)

        call.enqueue(object : Callback<ResponseHttp> {
            override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                if (response.isSuccessful) {
                    callback.success(response.body()?.message ?: "Registrado con éxito", "1")
                } else {
                    callback.error(response.body()?.message ?: "Ocurrío un error")
                }
            }

            override fun onFailure(p0: Call<ResponseHttp>, p1: Throwable) {
                val errorMessage = "Ocurrío un error"
                callback.error(errorMessage)
            }
        })
    }
}