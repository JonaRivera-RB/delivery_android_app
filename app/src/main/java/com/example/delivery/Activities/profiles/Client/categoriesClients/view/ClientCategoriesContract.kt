package com.example.delivery.Activities.profiles.Client.categoriesClients.view

import com.example.delivery.Activities.profiles.Restaurant.home.entities.Category

interface ClientCategoriesContract {

    interface View {
        fun showLoader(show: Boolean)
        fun showErrorView(error: String)
        fun updateCategories(categories: ArrayList<Category>)
    }

    interface Presenter {
        fun getCategories()
    }
}