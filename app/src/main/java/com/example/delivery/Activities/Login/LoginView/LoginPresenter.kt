package com.example.delivery.Activities.Login.LoginView

import com.example.delivery.Activities.Login.LoginData.LoginDataSource
import com.example.delivery.Activities.Login.LoginData.LoginRepository
import com.example.delivery.Activities.register.entities.User

class LoginPresenter (
    private val repository: LoginRepository,
    private val loginView: LoginContract.View
): LoginContract.Presenter {

    override fun login(email: String, password: String) {
        loginView.showLoader(true)

        repository.login(email, password, object : LoginDataSource.LoginCallback {

            override fun success(user: User) {
                loginView.showLoader(false)

                user.roles?.let { roles ->
                    if (roles.size > 1) {
                        loginView.showSelectRol()
                    } else {
                        loginView.showHome()
                    }
                }
            }

            override fun error(errorMessage: String) {
                loginView.showLoader(false)
                loginView.loginError(errorMessage)
            }
        })
    }
}