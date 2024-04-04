package com.example.delivery.data.utils

import android.util.Log
import com.example.delivery.data.models.ResponseHttp
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Response

object ResponseUtils {
    fun <T> Response<ResponseHttp>.deserializeToObject(clazz: Class<T>): T? {
        return body()?.let { responseBody ->
            try {
                Gson().fromJson(responseBody.data, clazz)
            } catch (e: JsonSyntaxException) {
                null
            }
        }
    }
}