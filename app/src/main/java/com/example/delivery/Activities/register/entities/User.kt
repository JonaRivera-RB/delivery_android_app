package com.example.delivery.Activities.register.entities

import android.os.Parcelable
import com.example.delivery.data.models.Rol
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
@Parcelize
data class User (
    @Expose
    val id: String? = null,

    @Expose
    var name: String ?= null,

    @Expose
    var lastname: String ?= null,

    @Expose
    val email: String ?= null,

    @Expose
    var phone: String ?= null,

    @Expose
    val password: String ?= null,

    @Expose
    val image: String? = null,

    //@SerializedName(value = "session_token")
    @Expose
    val session_token: String? = null,

    //@SerializedName(value = "is_available")
    @Expose
    val is_available: String? = null,

    @Expose
    val roles: ArrayList<Rol>? = null
): Parcelable {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}