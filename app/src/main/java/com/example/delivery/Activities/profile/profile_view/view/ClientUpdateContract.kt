package com.example.delivery.Activities.profile.profile_view.view

import com.example.delivery.Activities.register.entities.User
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ClientUpdateContract {

    interface View {
        fun showLoader(show: Boolean)
        fun returnView()
        fun showError(errorCode: String)
    }

    interface Presenter {
        fun updateUser(user: RequestBody, image: MultipartBody.Part)
        fun updateUserWithoutImage(user: User)
    }
}