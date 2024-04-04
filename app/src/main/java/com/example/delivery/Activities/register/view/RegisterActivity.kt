package com.example.delivery.Activities.register.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.delivery.Activities.Injection
import com.example.delivery.Activities.Login.LoginView.LoadingView
import com.example.delivery.Activities.Login.LoginView.LoginPresenter
import com.example.delivery.Extensions.isEmailValid
import com.example.delivery.data.models.api.Providers.UsersProvider
import com.example.delivery.R
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.data.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private var imageViewGoToLogin: ImageView? = null
    private var nameEditText: EditText? = null
    private var lastNameEditText: EditText? = null
    private var phoneNumberEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var confirmPasswordEditText: EditText? = null
    private var registerButton: Button? = null

    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(Injection.registerRepository(applicationContext), this)

        imageViewGoToLogin = findViewById(R.id.go_to_login_image_view)
        nameEditText = findViewById(R.id.name_edit_text)
        lastNameEditText = findViewById(R.id.last_name_edit_text)
        phoneNumberEditText = findViewById(R.id.phone_number_edit_text)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text)
        registerButton = findViewById(R.id.register_button)

        imageViewGoToLogin?.setOnClickListener {
            presenter.goToLoginView()
        }

        registerButton?.setOnClickListener {
            registerBtnWasPressed()
        }
    }

    private fun registerBtnWasPressed() {
        val name = nameEditText?.text.toString()
        val lastName = lastNameEditText?.text.toString()
        val phoneNumber = phoneNumberEditText?.text.toString()
        val email = emailEditText?.text.toString()
        val password = passwordEditText?.text.toString()
        val confirmPassword = confirmPasswordEditText?.text.toString()

        if (name.isNotEmpty() &&
            lastName.isNotEmpty() &&
            phoneNumber.isNotEmpty() &&
            email.isNotEmpty() &&
            password.isNotEmpty() &&
            confirmPassword.isNotEmpty() &&
            password == confirmPassword &&
            email.isEmailValid()) {
            val user = User(name = name,
                lastname = lastName,
                phone = phoneNumber,
                email = email,
                password = password)

            presenter.registerUser(user)
        } else {
            Toast.makeText(this, "Introduce los datos correctos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoader(show: Boolean) {
        if (show) LoadingView.showDialog(this, "Registrando usuario...")
        else LoadingView.hideDialog()
    }

    override fun showUserCreatedSuccess() {
        Toast.makeText(this, "usuario creado con éxito", Toast.LENGTH_SHORT).show()
    }

    override fun showErrorView(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun goToLoginView() {
        finish()
    }
}