package com.example.delivery.Activities.profile.save_image.data

import com.example.delivery.Activities.register.data.RegisterDataSource
import com.example.delivery.Activities.register.data.RegisterRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SaveImageRepository(
    private val saveImageRemote: SaveImageDataSource
): SaveImageDataSource {
    override fun updateImage(
        user: RequestBody,
        image: MultipartBody.Part,
        callback: SaveImageDataSource.SaveImageCallback,
    ) {
        saveImageRemote.updateImage(user, image, object : SaveImageDataSource.SaveImageCallback {
            override fun success() {
                callback.success()
            }

            override fun error(errorMessage: String) {
                callback.error(errorMessage)
            }
        })
    }

    companion object {
        private var INSTANCE: SaveImageRepository?= null

        fun getInstance(
            saveImageRemote: SaveImageDataSource
        ): SaveImageRepository {
            return INSTANCE ?: SaveImageRepository(
                saveImageRemote
            ).apply {
                INSTANCE = this
            }
        }
    }
}