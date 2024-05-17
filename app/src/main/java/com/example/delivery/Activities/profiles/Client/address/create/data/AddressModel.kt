package com.example.delivery.Activities.profiles.Client.address.create.data

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class AddressModel(
    @SerializedName("id") val id: String ?= null,
    @SerializedName("id_user") val idUser: String,
    @SerializedName("address") val address: String,
    @SerializedName("neighborhood") val neighborhood: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)