package com.example.delivery.Activities.Login.LoginData

import android.content.Context
import com.example.delivery.Activities.Login.Entities.LoginModel
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.data.models.NewResponseHttp
import com.example.delivery.data.utils.ResponseUtils.deserializeToObject
import com.example.delivery.utils.SessionManager
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class LoginRemote(private val context: Context): LoginDataSource {
    override fun login(email: String, password: String, callback: LoginDataSource.LoginCallback) {
        val call = RetrofitService.Builder()
            .getRetrofit(context)
            .getApi()
            .login(LoginModel(email, password))

        call.enqueue(object : Callback<NewResponseHttp>{
            override fun onResponse(call: Call<NewResponseHttp>, response: Response<NewResponseHttp>) {
                if(response.isSuccessful) {

                    val user: User? = response?.body()?.data

                    if (user != null ) {
                        val sesionToken = user.sessionToken ?: return callback.error("token invalido")
                        SessionManager.getInstance(context).setTokenSession(sesionToken)
                        SessionManager.getInstance(context).setRememberSession(true)
                        saveUserInSession(user)
                        callback.success(user)
                    } else {
                        callback.error("Error al obtener el ususario")
                    }

                    return
                }

                val errorMessage = response.body()?.message ?: "Ocurrío un error"
                callback.error(errorMessage)
            }

            override fun onFailure(call: Call<NewResponseHttp>, t: Throwable) {
                val errorMessage = "Ocurrío un error"
                callback.error(errorMessage)
            }
        })
    }

    private fun saveUserInSession(data: User?) {
        data?.let { SessionManager.getInstance(context).save("user", it) }
    }
}