package com.example.delivery.Activities.profile.profile_view.data

import RetrofitService
import android.content.Context
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.data.utils.ResponseUtils.deserializeToObject
import com.example.delivery.utils.SessionManager
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientUpdateRemote(private val context: Context): ClientUpdateDataSource {
    override fun updateUser(
        user: RequestBody,
        image: MultipartBody.Part,
        callback: ClientUpdateDataSource.UpdateUserCallback,
    ) {
        val call = RetrofitService.Builder()
            .getRetrofit(context)
            .getApi()
            .update(user, image)

        call.enqueue(object : Callback<ResponseHttp> {
            override fun onResponse(p0: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                val user = response.deserializeToObject(User::class.java)

                if (user != null) {
                    val sesionToken = user.session_token ?: return callback.error("token invalido")
                    SessionManager.getInstance(context).setTokenSession(sesionToken)
                    SessionManager.getInstance(context).setRememberSession(true)
                    saveUserInSession(user)
                    callback.success()
                } else {
                    callback.error("Error al obtener el ususario")
                }
            }

            override fun onFailure(p0: Call<ResponseHttp>, p1: Throwable) {
                callback.error("Some error")
            }

        })
    }

    override fun updateUserWithoutImage(
        user: User,
        callback: ClientUpdateDataSource.UpdateUserCallback,
    ) {
        val call = RetrofitService.Builder()
        .getRetrofit(context)
        .getApi()
        .updateWithoutImage(user)

        call.enqueue(object : Callback<ResponseHttp> {
            override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                if(response.isSuccessful) {
                    val user = response.deserializeToObject(User::class.java)

                    if (user != null) {
                        SessionManager.getInstance(context).setRememberSession(true)
                        saveUserInSession(user)
                        callback.success()
                    } else {
                        callback.error(response.body()?.message ?: "Ocurrío un error")
                    }
                }
            }

            override fun onFailure(p0: Call<ResponseHttp>, p1: Throwable) {
            callback.error("Some error")
            }
        })
    }

    private fun saveUserInSession(data: User?) {
        data?.let { SessionManager.getInstance(context).save("user", it) }
    }
}