package com.example.delivery.Activities.Client.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.delivery.Activities.Login.LoginView.LoginActivity
import com.example.delivery.R
import com.example.delivery.data.models.User
import com.example.delivery.utils.SessionManager
import com.example.delivery.utils.SharedPref
import com.google.gson.Gson

class ClientHomeActivity : AppCompatActivity() {

    private var logoutButton: Button ?= null
    private var sharedPreferences: SharedPref ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_home)
        getUserFromSession()

        sharedPreferences = SharedPref(this)
        logoutButton = findViewById(R.id.logout_button)
        logoutButton?.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        sharedPreferences?.remove("user")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        SessionManager.getInstance(applicationContext).setRememberSession(false)
    }

    private fun getUserFromSession() {
        val gson = Gson()

        if(!sharedPreferences?.getData("user").isNullOrBlank()) {
           //si el usuario existe en sesion
            val user = gson.fromJson(sharedPreferences?.getData("user"), User::class.java)
            Log.d("USER", "el usuario es: $user")
        }
    }
}