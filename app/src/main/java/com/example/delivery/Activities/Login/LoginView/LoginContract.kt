package com.example.delivery.Activities.Login.LoginView

interface LoginContract {
    interface View {
        fun showLoader(show: Boolean)
        fun showHome()
        fun showSelectRol()
        fun loginError(errorCode: String)
    }

    interface Presenter {
        fun login(email: String, password: String)
    }
}