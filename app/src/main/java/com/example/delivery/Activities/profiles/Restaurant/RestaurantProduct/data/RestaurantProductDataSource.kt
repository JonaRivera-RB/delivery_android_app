package com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.data

import com.example.delivery.Activities.profiles.Restaurant.home.entities.Category

interface RestaurantProductDataSource {

    interface RestaurantProductCallback {
        fun success(categories: ArrayList<Category>)
        fun error(error: String)
    }

    fun getCategories(callback: RestaurantProductCallback)
}