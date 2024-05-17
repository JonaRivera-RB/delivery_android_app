package com.example.delivery.Activities

import android.content.Context
import com.example.delivery.Activities.Login.LoginData.LoginRemote
import com.example.delivery.Activities.Login.LoginData.LoginRepository
import com.example.delivery.Activities.profile.profile_view.data.ClientUpdateRemote
import com.example.delivery.Activities.profile.profile_view.data.ClientUpdateRepository
import com.example.delivery.Activities.profile.save_image.data.SaveImageRemote
import com.example.delivery.Activities.profile.save_image.data.SaveImageRepository
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.data.RestaurantProductDataSource
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.data.RestaurantProductRemote
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.data.RestaurantProductRepository
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.view.RestaurantProductPresenter
import com.example.delivery.Activities.register.data.RegisterRemote
import com.example.delivery.Activities.register.data.RegisterRepository

object Injection {

    fun loginRepository(context: Context): LoginRepository {
        return LoginRepository.getInstance(LoginRemote(context))
    }

    fun registerRepository(context: Context): RegisterRepository {
        return RegisterRepository.getInstance(RegisterRemote(context))
    }

    fun saveImageRepository(context: Context): SaveImageRepository {
        return SaveImageRepository.getInstance(SaveImageRemote(context))
    }

    fun updateUserRepository(context: Context): ClientUpdateRepository {
        return ClientUpdateRepository.getInstance(ClientUpdateRemote(context))
    }

    fun restaurantProductRepository(context: Context): RestaurantProductRepository {
        return RestaurantProductRepository.getInstance(RestaurantProductRemote(context))
    }
}