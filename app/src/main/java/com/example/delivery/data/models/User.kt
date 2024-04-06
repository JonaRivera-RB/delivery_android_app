package com.example.delivery.data.models

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class User (
    @Expose
    val id: String? = null,

    @Expose
    val name: String ?= null,

    @Expose
    val lastname: String ?= null,

    @Expose
    val email: String ?= null,

    @Expose
    val phone: String ?= null,

    @Expose
    val password: String ?= null,

    @Expose
    val image: String? = null,

    @Expose
    @SerializedName(value = "session_token")
    val sessionToken: String? = null,

    @Expose
    @SerializedName(value = "is_available")
    val isAvailable: String? = null,

    @Expose
    val roles: ArrayList<Rol>? = null
): Parcelable