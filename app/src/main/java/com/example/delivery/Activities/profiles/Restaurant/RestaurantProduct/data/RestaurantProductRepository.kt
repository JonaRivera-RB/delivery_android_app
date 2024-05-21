package com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.data

import com.example.delivery.Activities.profile.profile_view.data.ClientUpdateDataSource
import com.example.delivery.Activities.profile.profile_view.data.ClientUpdateRepository
import com.example.delivery.Activities.profiles.Restaurant.home.entities.Category

class RestaurantProductRepository(
    private val resturantProductDataSource: RestaurantProductDataSource
): RestaurantProductDataSource {
    override fun getCategories(callback: RestaurantProductDataSource.RestaurantProductCallback) {
        resturantProductDataSource.getCategories(object :
            RestaurantProductDataSource.RestaurantProductCallback {

            override fun success(categories: ArrayList<Category>) {
                callback.success(categories)
            }

            override fun error(error: String) {
            callback.error(error)
            }
        })
    }


    companion object {
        private var INSTANCE: RestaurantProductRepository?= null

        fun getInstance(
            restaurantProductRemote: RestaurantProductDataSource
        ): RestaurantProductRepository {
            return INSTANCE ?: RestaurantProductRepository(
                restaurantProductRemote
            ).apply {
                INSTANCE = this
            }
        }
    }
}