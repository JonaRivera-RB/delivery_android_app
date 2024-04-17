package com.example.delivery.utils

import android.content.Context
import android.util.Log
import com.example.delivery.utils.enums.Roles
import com.google.gson.Gson
import java.lang.Exception

class SessionManager(context: Context) {

    private val preferences = context.getSharedPreferences(
        AppConstants.SESSION_PREF_NAME,
        Context.MODE_PRIVATE
    )

    fun isRememberSession(): Boolean = preferences.getBoolean(AppConstants.REMEMBER_SESSION_KEY, false)

    fun setRememberSession(rememberSession: Boolean) {
        preferences.edit().putBoolean(AppConstants.REMEMBER_SESSION_KEY, rememberSession).apply()
    }
    fun getUserRol(): String? = preferences.getString(AppConstants.USER_ROL, null)

    fun setUserRol(rol: String) {
        preferences.edit().putString(AppConstants.USER_ROL, rol).apply()
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

    fun <T>getDataFromPreferences(key: String, clazz: Class<T>): T? {
        try {
            val json = preferences.getString(key, null)
            val gson = Gson()
            return gson.fromJson(json, clazz)
        } catch (e: Exception) {
            Log.d("ERROR", "Error: ${e.message}")
        }

        return null
    }

    fun remove(key: String) {
        preferences?.edit()?.remove(key)?.apply()
    }

    companion object {
        private var INSTANCE: SessionManager ?= null

        fun getInstance(context: Context): SessionManager {
            return INSTANCE ?: SessionManager(context).apply {
                INSTANCE = this
            }
        }
    }
}