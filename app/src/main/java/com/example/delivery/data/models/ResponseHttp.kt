package com.example.delivery.data.models

import android.os.Parcelable
import com.example.delivery.Activities.register.entities.User
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseHttp(
    @Expose
    var message: String,

    @Expose
    var success: Boolean,

    @Expose
    var dataString: String? = null, // Representación en forma de String del JsonObject

    @Expose
    var error: String? = null
) : Parcelable {

    val data: JsonObject? = dataString?.let { jsonString ->
        try {
            Gson().fromJson(jsonString, JsonObject::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }
}
class NewResponseHttp(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: User?
)