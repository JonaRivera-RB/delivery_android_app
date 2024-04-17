package com.example.delivery.Activities.Login.Entities

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class LoginModel (
    val email: String,
    val password: String
)