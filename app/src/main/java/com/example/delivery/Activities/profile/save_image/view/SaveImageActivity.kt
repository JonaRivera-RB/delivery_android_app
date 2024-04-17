package com.example.delivery.Activities.profile.save_image.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.delivery.Activities.Injection
import com.example.delivery.R
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.utils.SessionManager
import com.example.delivery.utils.objects.LoadingView
import com.github.dhaval2404.imagepicker.ImagePicker
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class SaveImageActivity : AppCompatActivity(), SaveImageContract.View {

    private var circleImageUser: CircleImageView ?= null
    private var confirmButton: Button ?= null
    private var skipButton: Button ?= null

    private var imageFile: File?= null
    private lateinit var presenter: SaveImagePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_image)

        presenter = SaveImagePresenter(Injection.saveImageRepository(applicationContext), this)

        initView()
        initListeners()
    }
    private fun initView() {
        circleImageUser = findViewById(R.id.user_image_view)
        confirmButton = findViewById(R.id.confirm_button)
        skipButton = findViewById(R.id.skip_button)
    }

    private fun initListeners() {
        circleImageUser?.setOnClickListener {
            selectImage()
        }

        confirmButton?.setOnClickListener {
            val requestFile = imageFile?.asRequestBody("image/*".toMediaTypeOrNull())
            val image = requestFile?.let { MultipartBody.Part.createFormData("image", imageFile?.name, it) }

            val user = SessionManager.getInstance(this).getDataFromPreferences("user", User::class.java)
            val requestBody = user?.toJson()?.toRequestBody("text/plain".toMediaTypeOrNull())

            if (image != null && requestBody != null) {
                presenter.updateImage(requestBody, image)
            }
        }
    }

    private val startImageForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data

        when (resultCode) {
            Activity.RESULT_OK -> {
                val fileUri = data?.data
                imageFile = fileUri?.path?.let { File(it) }
                circleImageUser?.setImageURI(fileUri)
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

    override fun showHome() {
        Toast.makeText(this, "todo chido", Toast.LENGTH_SHORT).show()
    }

    override fun showError(errorCode: String) {
        Toast.makeText(this, errorCode, Toast.LENGTH_SHORT).show()
    }
}