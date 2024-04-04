package com.example.delivery.Activities.Login.Entities

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class LoginModel (
    @Expose
    val email: String,

    @Expose
    val password: String
)