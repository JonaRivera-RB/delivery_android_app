package com.example.delivery.data.models

import com.google.gson.annotations.SerializedName

class Rol(
    @SerializedName(value= "id") val id: String,
    @SerializedName(value= "name") val name: String,
    @SerializedName(value= "image") val image: String
) {
    override fun toString(): String {
        return "Rol(id='$id', name='$name', image='$image')"
    }
}