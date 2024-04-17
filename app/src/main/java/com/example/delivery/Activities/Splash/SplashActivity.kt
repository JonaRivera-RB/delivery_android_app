package com.example.delivery.Activities.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.delivery.Activities.profiles.Client.Home.view.ClientHomeActivity
import com.example.delivery.Activities.profiles.Delivery.home.view.DeliveryActivity
import com.example.delivery.Activities.Login.LoginView.LoginActivity
import com.example.delivery.Activities.profiles.Restaurant.home.view.RestaurantActivity
import com.example.delivery.R
import com.example.delivery.utils.SessionManager
import com.example.delivery.utils.enums.Roles

class SplashActivity : AppCompatActivity(), SplashContract.View {

    private val SPLASH_TIME_OUT: Long = 3000 // 3 segundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        findViewById<View>(android.R.id.content).postDelayed({
         val presenter = SplashPresenter(this)
            presenter.checkForNewVersion()
        }, SPLASH_TIME_OUT)
    }

    override fun showUpdateAppView() {

    }

    override fun showNextActivity() {
        startActivity(getCurrentIntent())
        finish()
    }

    private fun getCurrentIntent(): Intent {
        val session = SessionManager.getInstance(applicationContext)

        return when (session.getUserRol()) {
            Roles.client.label -> Intent(this, ClientHomeActivity::class.java)
            Roles.restaurant.label -> Intent(this, RestaurantActivity::class.java)
            Roles.delivery.label -> Intent(this, DeliveryActivity::class.java)
            else -> Intent(this, LoginActivity::class.java)
        }
    }
}