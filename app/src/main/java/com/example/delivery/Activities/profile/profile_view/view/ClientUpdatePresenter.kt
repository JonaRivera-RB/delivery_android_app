package com.example.delivery.Activities.profile.profile_view.view

import com.example.delivery.Activities.profile.profile_view.data.ClientUpdateDataSource
import com.example.delivery.Activities.profile.profile_view.data.ClientUpdateRepository
import com.example.delivery.Activities.register.entities.User
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ClientUpdatePresenter(
    private val repository: ClientUpdateRepository,
    private val clientUpdateView: ClientUpdateContract.View
): ClientUpdateContract.Presenter {
    override fun updateUser(user: RequestBody, image: MultipartBody.Part) {
        clientUpdateView.showLoader(true)

        repository.updateUser(user, image, object : ClientUpdateDataSource.UpdateUserCallback {
            override fun success() {
                clientUpdateView.showLoader(false)
                clientUpdateView.returnView()
            }

            override fun error(errorMessage: String) {
                clientUpdateView.showLoader(false)
                clientUpdateView.showError(errorMessage)
            }

        })
    }

    override fun updateUserWithoutImage(user: User) {
        clientUpdateView.showLoader(true)

        repository.updateUserWithoutImage(user, object : ClientUpdateDataSource.UpdateUserCallback {
            override fun success() {
                clientUpdateView.showLoader(false)
                clientUpdateView.returnView()
            }

            override fun error(errorMessage: String) {
                clientUpdateView.showLoader(false)
                clientUpdateView.showError(errorMessage)
            }
        })
    }
}