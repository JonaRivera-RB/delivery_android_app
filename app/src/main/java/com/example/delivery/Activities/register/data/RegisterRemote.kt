package com.example.delivery.Activities.register.data

import android.content.Context
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.data.utils.ResponseUtils.deserializeToObject
import com.example.delivery.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRemote(private val context: Context): RegisterDataSource {

    override fun register(user: User, callback: RegisterDataSource.RegisterCallback) {
        val call = RetrofitService.Builder()
            .getRetrofit(context)
            .getApi()
            .register(user)

        call.enqueue(object : Callback<ResponseHttp> {
            override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                if(response.isSuccessful) {
                    //REFACTOR
                    val user: User? = response.deserializeToObject(User::class.java)

                    if (user != null ) {
                        val sesionToken = user.session_token ?: return callback.error("token invalido")
                        SessionManager.getInstance(context).setTokenSession(sesionToken)
                        SessionManager.getInstance(context).setRememberSession(true)
                        saveUserInSession(user)
                        callback.success(response.body()?.message ?: "Registrado con éxito", "1")
                    } else {
                        callback.error("Error al obtener el ususario")
                    }

                    return
                }

                val errorMessage = response.body()?.message ?: "Ocurrío un error"
                callback.error(errorMessage)
            }

            override fun onFailure(p0: Call<ResponseHttp>, p1: Throwable) {
                val errorMessage = "Ocurrío un error"
                callback.error(errorMessage)
            }
        })
    }
    private fun saveUserInSession(data: User?) {
        data?.let { SessionManager.getInstance(context).save("user", it) }
    }
}