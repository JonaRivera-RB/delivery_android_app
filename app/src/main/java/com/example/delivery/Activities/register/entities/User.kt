package com.example.delivery.Activities.register.entities

import android.os.Parcelable
import com.example.delivery.data.models.Rol
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
class User (
    @SerializedName(value = "id")
    val id: String? = null,
    @SerializedName(value = "name")
    var name: String ?= null,
    @SerializedName(value = "lastname")
    var lastname: String ?= null,
    @SerializedName(value = "email")
    val email: String ?= null,
    @SerializedName(value = "phone")
    var phone: String ?= null,
    @SerializedName(value = "password")
    val password: String ?= null,
    @SerializedName(value = "image")
    val image: String? = null,
    @SerializedName(value = "session_token")
    val sessionToken: String? = null,
    @SerializedName(value = "is_available")
    val isAvailable: String? = null,
    @SerializedName(value = "roles")
    val roles: ArrayList<Rol>? = null
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }

    override fun toString(): String {
        return "${name} ${lastname}"
    }
}