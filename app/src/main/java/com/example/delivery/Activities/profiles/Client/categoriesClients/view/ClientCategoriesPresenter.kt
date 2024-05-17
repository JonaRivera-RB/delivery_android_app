package com.example.delivery.Activities.profiles.Client.categoriesClients.view

import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.data.RestaurantProductDataSource
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.data.RestaurantProductRepository
import com.example.delivery.Activities.profiles.Restaurant.home.entities.Category

class ClientCategoriesPresenter(
    private val repository: RestaurantProductRepository,
    private val clientCategoriesView: ClientCategoriesContract.View
): ClientCategoriesContract.Presenter {
    override fun getCategories() {
        clientCategoriesView.showLoader(true)

        repository.getCategories(object: RestaurantProductDataSource.RestaurantProductCallback {
            override fun success(categories: ArrayList<Category>) {
                clientCategoriesView.showLoader(false)
                clientCategoriesView.updateCategories(categories)
            }

            override fun error(error: String) {
                clientCategoriesView.showLoader(false)
                clientCategoriesView.showErrorView(error)
            }

        })
    }
}