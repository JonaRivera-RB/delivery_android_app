package com.example.delivery.data.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Url
import java.net.URL

class Rol(
    @SerializedName(value = "id")
    val id: Int ?= null,
    @SerializedName(value = "name")
    val name: String ?= null,
    @SerializedName(value = "imagen")
    val imagen: String ?= null
)