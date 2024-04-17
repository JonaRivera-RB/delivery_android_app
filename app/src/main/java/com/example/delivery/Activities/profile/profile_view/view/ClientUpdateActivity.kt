package com.example.delivery.Activities.profile.profile_view.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.delivery.Activities.Injection
import com.example.delivery.Activities.profile.save_image.view.SaveImagePresenter
import com.example.delivery.R
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.data.utils.ResponseUtils.deserializeToObject
import com.example.delivery.utils.SessionManager
import com.example.delivery.utils.objects.LoadingView
import com.github.dhaval2404.imagepicker.ImagePicker
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ClientUpdateActivity : AppCompatActivity(), ClientUpdateContract.View {

    private lateinit var imageUserCircle: CircleImageView
    private lateinit var nameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var updateButton: Button

    private var user: User?= null
    private var imageFile: File?= null

    private lateinit var presenter: ClientUpdatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_update)

        presenter = ClientUpdatePresenter(Injection.updateUserRepository(this), this)
        imageUserCircle = findViewById(R.id.user_image_view)
        nameEditText = findViewById(R.id.name_edit_text)
        lastNameEditText = findViewById(R.id.last_name_edit_text)
        phoneNumberEditText = findViewById(R.id.phone_number_edit_text)
        updateButton = findViewById(R.id.update_profile_button)

        getUserFromSession()

        nameEditText.setText(user?.name)
        lastNameEditText.setText(user?.lastname)
        phoneNumberEditText.setText(user?.phone)

        if(!user?.image.isNullOrBlank()) {
            Glide.with(this).load(user?.image).into(imageUserCircle)
        }

        imageUserCircle.setOnClickListener {
            selectImage()
        }

        updateButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()

            if (name.isEmpty() && lastName.isEmpty() && phoneNumber.isEmpty()) {
                return@setOnClickListener
            }

            user?.name = name
            user?.lastname = lastName
            user?.phone = phoneNumber


            if(imageFile == null && user != null) {
                presenter.updateUserWithoutImage(user!!)
            } else {
                val requestFile = imageFile?.asRequestBody("image/".toMediaTypeOrNull())
                val image = requestFile?.let { it1 ->
                    MultipartBody.Part.createFormData("image", imageFile?.name,
                        it1
                    )
                }

                val requestBody = user?.toJson()?.toRequestBody("text/plain".toMediaTypeOrNull())

                if (requestBody != null && image != null) {
                    presenter.updateUser(requestBody, image)
                } else {
                    //show alert
                }
            }
        }
    }
    private fun getUserFromSession() {
        user = SessionManager.getInstance(this).getDataFromPreferences("user", User::class.java)
    }

    private val startImageForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data

        when (resultCode) {
            Activity.RESULT_OK -> {
                val fileUri = data?.data
                imageFile = fileUri?.path?.let { File(it) }
                imageUserCircle.setImageURI(fileUri)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, "Tarea se cancelo", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun selectImage() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080,1080)
            .createIntent { intent ->
                startImageForResult.launch(intent)
            }
    }

    override fun showLoader(show: Boolean) {
        if (show) LoadingView.showDialog(this, "Guardando imagen...")
        else LoadingView.hideDialog()
    }

    override fun returnView() {
        Toast.makeText(this, "Todo chido", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun showError(errorCode: String) {
        Toast.makeText(this, errorCode, Toast.LENGTH_SHORT).show()
    }
}