package com.example.delivery.Activities.profile.save_image.data

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

class SaveImageRemote(private val context: Context): SaveImageDataSource {
    override fun updateImage(
        user: RequestBody,
        image: MultipartBody.Part,
        callback: SaveImageDataSource.SaveImageCallback,
    ) {
        val call = RetrofitService.Builder()
            .getRetrofit(context)
            .getApi()
            .update(user, image)

        call.enqueue(object : Callback<ResponseHttp> {
            override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {
                if (response.isSuccessful) {
                    val user: User? = response.deserializeToObject(User::class.java)

                    if (user != null) {
                        val sesionToken = user.session_token ?: return callback.error("token invalido")
                        SessionManager.getInstance(context).setTokenSession(sesionToken)
                        SessionManager.getInstance(context).setRememberSession(true)
                        saveUserInSession(user)
                        callback.success()
                    } else {
                        callback.error(response.body()?.message ?: "Registrado con éxito")
                    }
                }
            }

            override fun onFailure(p0: Call<ResponseHttp>, p1: Throwable) {
                callback.error("some error")
            }
        })
    }

    private fun saveUserInSession(data: User?) {
        data?.let { SessionManager.getInstance(context).save("user", it) }
    }
}