package com.example.delivery.data.models

import com.google.gson.annotations.SerializedName

class User (
    @SerializedName(value = "id") val id: String? = null,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "lastname") val lastname: String,
    @SerializedName(value = "email") val email: String,
    @SerializedName(value = "phone") val phone: String,
    @SerializedName(value = "password") val password: String,
    @SerializedName(value = "image") val image: String? = null,
    @SerializedName(value = "session_token") val sessionToken: String? = null,
    @SerializedName(value = "is_available") val isAvailable: String? = null,
    @SerializedName(value = "roles") val roles: ArrayList<Rol>? = null
) {
    override fun toString(): String {
        return "User(id=$id, name='$name', lastname='$lastname', email='$email', phone='$phone', password='$password', image=$image, sessionToken=$sessionToken, isAvailable=$isAvailable, roles=$roles)"
    }
}