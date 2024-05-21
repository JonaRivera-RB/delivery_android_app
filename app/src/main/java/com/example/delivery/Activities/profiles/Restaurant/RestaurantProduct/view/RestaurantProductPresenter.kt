package com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.view

import com.example.delivery.Activities.profiles.Restaurant.home.entities.Category
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.data.RestaurantProductDataSource
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.data.RestaurantProductRepository

class RestaurantProductPresenter(
    private val repository: RestaurantProductRepository,
    private val restaurantProductView: RestaurantProductContract.View
): RestaurantProductContract.Presenter {
    override fun getCategories() {
        restaurantProductView.showLoader(true)

        repository.getCategories(object : RestaurantProductDataSource.RestaurantProductCallback {
            override fun success(categories: ArrayList<Category>) {
                restaurantProductView.showLoader(false)
                restaurantProductView.updateCategories(categories)
            }

            override fun error(error: String) {
                restaurantProductView.showLoader(false)
                restaurantProductView.showErrorView(error)
            }
        })
    }
}