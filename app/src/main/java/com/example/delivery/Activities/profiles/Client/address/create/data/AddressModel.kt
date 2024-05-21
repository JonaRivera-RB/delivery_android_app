package com.example.delivery.Activities.profiles.Client.address.create.data

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class AddressModel(
    val id: String ?= null,

    val id_user: String,

    val address: String,

    val neighborhood: String,

    val lat: Double,

    val lng: Double
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}