package com.example.delivery.Activities.profile.profile_view.data

import com.example.delivery.Activities.register.entities.User
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ClientUpdateDataSource {

    interface UpdateUserCallback {
        fun success()
        fun error(errorMessage: String)
    }

    fun updateUser(user: RequestBody, image: MultipartBody.Part, callback: UpdateUserCallback)
    fun updateUserWithoutImage(user: User, callback: UpdateUserCallback)
}