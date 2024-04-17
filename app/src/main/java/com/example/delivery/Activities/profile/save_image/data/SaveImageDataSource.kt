package com.example.delivery.Activities.profile.save_image.data

import okhttp3.MultipartBody
import okhttp3.RequestBody

interface SaveImageDataSource {

    interface SaveImageCallback {
        fun success()
        fun error(errorMessage: String)
    }

    fun updateImage(user: RequestBody, image: MultipartBody.Part, callback: SaveImageCallback)
}