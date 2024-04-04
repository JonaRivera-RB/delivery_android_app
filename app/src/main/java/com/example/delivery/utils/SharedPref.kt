package com.example.delivery.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import java.lang.Exception

class SharedPref(activity: Activity) {

    private var preferences: SharedPreferences? = null

    init {
        preferences = activity.getSharedPreferences("com.example.delivery", Context.MODE_PRIVATE)
    }

    fun save(key: String, obj: Any) {
        try {
            val gson = Gson()
            val json = gson.toJson(obj)
            with(preferences?.edit()) {
                this?.putString(key, json)
                this?.commit()
            }
        } catch (e: Exception) {
            Log.d("ERROR", "Error: ${e.message}")
        }
    }

    fun getData(key: String): String? {
        return preferences?.getString(key, "")
    }

    fun remove(key: String) {
        preferences?.edit()?.remove(key)?.apply()
    }
}