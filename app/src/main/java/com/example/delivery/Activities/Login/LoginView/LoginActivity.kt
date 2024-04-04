package com.example.delivery.Activities.Login.LoginView

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.delivery.Activities.Client.Home.ClientHomeActivity
import com.example.delivery.Activities.Injection
import com.example.delivery.Activities.register.view.RegisterActivity
import com.example.delivery.Activities.select_roles.view.SelectRolesActivity
import com.example.delivery.Extensions.isEmailValid
import com.example.delivery.R
import com.example.delivery.data.models.User
import com.example.delivery.utils.SharedPref
import com.google.gson.Gson

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private var goToRegisterImageView: ImageView? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var loginButton: Button? = null

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = LoginPresenter(Injection.loginRepository(applicationContext), this)
        goToRegisterImageView = findViewById(R.id.register_image_view)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)

        goToRegisterImageView?.setOnClickListener {
            goToRegister()
        }

        loginButton?.setOnClickListener {
            loginBtnWasPressed()
        }
    }

    private fun goToRegister() {
        val registerActivity = Intent(this, RegisterActivity::class.java)
        startActivity(registerActivity)
    }

    private fun loginBtnWasPressed() {
        val email = emailEditText?.text.toString()
        val password = passwordEditText?.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() && email.isEmailValid()) {

            presenter.login(email, password)
        }
    }

    override fun showLoader(show: Boolean) {
        if (show) LoadingView.showDialog(this, "Iniciando Sesión")
        else LoadingView.hideDialog()
    }

    override fun showHome() {
        val intent = Intent(this, ClientHomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showSelectRol() {
        val intent = Intent(this, SelectRolesActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun loginError(errorCode: String) {
        Toast.makeText(this@LoginActivity, errorCode, Toast.LENGTH_SHORT).show()
    }


    private fun saveUserInSession(data: String) {
        val sharedPreferences = SharedPref(this)
        val gson = Gson()
        val user = gson.fromJson(data, User::class.java)
        sharedPreferences.save("user", user)
    }
}




//TODO refactor
object LoadingView {

    private var dialog: Dialog? = null

    fun showDialog(activity: Activity, message: String) {
        dialog = Dialog(activity, R.style.ThemeDialogTransparent)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.loader_view)

        val loaderText = dialog?.findViewById<TextView>(R.id.loadingMessage)
        loaderText?.text = message
        dialog?.show()
    }

    fun hideDialog() {
        try {
            dialog?.dismiss()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }
}