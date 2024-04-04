package com.example.delivery.Activities.Login.LoginData

import android.content.Context
import com.example.delivery.Activities.Login.Entities.LoginModel
import com.example.delivery.data.api.RetrofitService
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.data.models.Rol
import com.example.delivery.data.models.User
import com.example.delivery.data.utils.ResponseUtils.deserializeToObject
import com.example.delivery.utils.SessionManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.InstanceCreator
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.lang.reflect.Type

class LoginRemote(private val context: Context): LoginDataSource {
    override fun login(email: String, password: String, callback: LoginDataSource.LoginCallback) {
        val call = RetrofitService.Builder()
            .addHeaders(context)
            .getApi()
            .login(LoginModel(email, password))

        call.enqueue(object : Callback<ResponseHttp>{
            override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                if(response.isSuccessful && response.body()?.success == true) {
                    val user: User? = response.deserializeToObject(User::class.java)

                    val roles: ArrayList<Rol> = Gson().fromJson(user?.roles.toString(), ArrayList<Rol>::class.java)


                    if (user != null && roles.size > 0) {
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

            override fun onFailure(call: Call<ResponseHttp>, t: Throwable) {
                val errorMessage = "Ocurrío un error"
                callback.error(errorMessage)
            }
        })
    }

    private fun saveUserInSession(data: User?) {
        data?.let { SessionManager.getInstance(context).save("user", it) }
    }
}