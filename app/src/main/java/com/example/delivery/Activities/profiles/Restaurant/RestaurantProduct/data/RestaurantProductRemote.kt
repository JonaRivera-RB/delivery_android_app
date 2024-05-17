package com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.data

import android.R
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.delivery.Activities.profiles.Restaurant.home.entities.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantProductRemote(private val context: Context): RestaurantProductDataSource {
    override fun getCategories(callback: RestaurantProductDataSource.RestaurantProductCallback) {
        val call = RetrofitService.Builder()
            .getRetrofit(context)
            .getApi()
            .getAll()

        call.enqueue(object: Callback<ArrayList<Category>> {
            override fun onResponse(
                p0: Call<ArrayList<Category>>,
                response: Response<ArrayList<Category>>,
            ) {

                if(response.body() != null) {
                    val categories = response.body() as ArrayList<Category>
                    callback.success(categories)
                } else {
                    callback.error("ocurrío un error al obtener las categorias")
                }
            }

            override fun onFailure(p0: Call<ArrayList<Category>>, p1: Throwable) {
                callback.error("ocurrío un error de red")
            }
        })
    }
}