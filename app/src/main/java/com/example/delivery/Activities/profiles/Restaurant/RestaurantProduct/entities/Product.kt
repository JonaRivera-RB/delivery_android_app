package com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.entities

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class Product(
    @SerializedName("id") val id: String ?= null,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("image1") val image1: String ?= null,
    @SerializedName("image2") val image2: String ?= null,
    @SerializedName("image3") val image3: String ?= null,
    @SerializedName("id_category") val id_category: String,
    @SerializedName("price") val price: Double,
    @SerializedName("quantity") var quantity: Int ?= null
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }

    override fun toString(): String {
        return "Product(id='$id', name='$name', description='$description', image1='$image1', image2='$image2', image3='$image3', category='$id_category', price=$price, quantity=$quantity)"
    }
}