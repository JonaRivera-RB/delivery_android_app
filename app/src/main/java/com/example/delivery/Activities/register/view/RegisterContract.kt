package com.example.delivery.Activities.register.view

import com.example.delivery.Activities.register.entities.User

interface RegisterContract {

    interface View {
        fun showLoader(show: Boolean)
        fun showUserCreatedSuccess()
        fun showErrorView(error: String)
        fun goToLoginView()
    }

    interface Presenter {
        fun registerUser(user: User)
        fun goToLoginView()
    }
}