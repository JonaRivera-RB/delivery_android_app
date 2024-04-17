package com.example.delivery.Activities.profile.save_image.view

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

interface SaveImageContract {

    interface View {
        fun showLoader(show: Boolean)
        fun showHome()
        fun showError(errorCode: String)
    }

    interface Presenter {
        fun updateImage(user: RequestBody, image: MultipartBody.Part)
    }
}