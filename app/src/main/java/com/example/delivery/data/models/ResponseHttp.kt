package com.example.delivery.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName

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


@Parcelize
data class ResponseModel(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val userData: UserData
) : Parcelable

@Parcelize
data class UserData(
    @SerializedName("id") val id: String ?= null,
    @SerializedName("lastname") val lastname: String ?= null,
    @SerializedName("email") val email: String ?= null,
    @SerializedName("phone") val phone: String ?= null,
    @SerializedName("image") val image: String ?= null,
    @SerializedName("session_token") val sessionToken: String ?= null,
    @SerializedName("roles") val roles: List<Role> ?= null
) : Parcelable

@Parcelize
data class Role(
    @SerializedName("id") val id: Int ?= null,
    @SerializedName("name") val name: String ?= null,
    @SerializedName("image") val image: String ?= null
) : Parcelable