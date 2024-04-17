package com.example.delivery.Activities.register.view

import com.example.delivery.Activities.register.data.RegisterDataSource
import com.example.delivery.Activities.register.data.RegisterRepository
import com.example.delivery.Activities.register.entities.User

class RegisterPresenter(
    private val repository: RegisterRepository,
    private val registerView: RegisterContract.View
): RegisterContract.Presenter {

    override fun registerUser(user: User) {
        registerView.showLoader(true)

        repository.register(user, object : RegisterDataSource.RegisterCallback {
            override fun success(message: String, id: String) {
                registerView.showLoader(false)
                registerView.showUserCreatedSuccess()
            }

            override fun error(error: String) {
                registerView.showLoader(false)
                registerView.showErrorView(error)
            }
        })
    }

    override fun goToLoginView() {
        registerView.goToLoginView()
    }
}