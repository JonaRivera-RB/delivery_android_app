package com.example.delivery.data.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Url
import java.net.URL

@Parcelize
data class Rol(
    @Expose
    val id: Int ?= null,

    @Expose
    val name: String ?= null,

    @Expose
    val imagen: String ?= null
) : Parcelable