package com.example.delivery.Activities.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.delivery.Activities.Client.Home.ClientHomeActivity
import com.example.delivery.Activities.Login.LoginView.LoginActivity
import com.example.delivery.R
import com.example.delivery.utils.SessionManager

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

        return when {
            session.isRememberSession() -> Intent(this, ClientHomeActivity::class.java)
            else -> Intent(this, LoginActivity::class.java)
        }
    }
}