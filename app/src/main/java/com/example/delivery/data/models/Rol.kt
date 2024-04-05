package com.example.delivery.data.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rol(
    @Expose
    val name: String ?= null,

): Parcelable