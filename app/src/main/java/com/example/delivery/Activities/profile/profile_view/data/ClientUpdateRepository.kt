package com.example.delivery.Activities.profile.profile_view.data

import com.example.delivery.Activities.profile.save_image.data.SaveImageDataSource
import com.example.delivery.Activities.profile.save_image.data.SaveImageRepository
import com.example.delivery.Activities.register.entities.User
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ClientUpdateRepository(
    private val clientUpdateRemote: ClientUpdateDataSource
): ClientUpdateDataSource {
    override fun updateUser(
        user: RequestBody,
        image: MultipartBody.Part,
        callback: ClientUpdateDataSource.UpdateUserCallback,
    ) {
        clientUpdateRemote.updateUser(user, image, object: ClientUpdateDataSource.UpdateUserCallback{
            override fun success() {
                callback.success()
            }

            override fun error(errorMessage: String) {
                callback.error(errorMessage)
            }

        })
    }

    override fun updateUserWithoutImage(
        user: User,
        callback: ClientUpdateDataSource.UpdateUserCallback,
    ) {
        clientUpdateRemote.updateUserWithoutImage(user, object : ClientUpdateDataSource.UpdateUserCallback {
            override fun success() {
                callback.success()
            }

            override fun error(errorMessage: String) {
            callback.error(errorMessage)
            }
        })
    }

    companion object {
        private var INSTANCE: ClientUpdateRepository?= null

        fun getInstance(
            clientUpdateRemote: ClientUpdateDataSource
        ): ClientUpdateRepository {
            return INSTANCE ?: ClientUpdateRepository(
                clientUpdateRemote
            ).apply {
                INSTANCE = this
            }
        }
    }
}