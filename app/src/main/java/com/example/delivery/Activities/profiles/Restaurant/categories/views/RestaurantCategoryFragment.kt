package com.example.delivery.Activities.profiles.Restaurant.categories.views

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.delivery.Activities.profiles.Restaurant.home.entities.Category
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.R
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.data.utils.ResponseUtils.deserializeToObject
import com.example.delivery.utils.SessionManager
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RestaurantCategoryFragment : Fragment() {

    private var myView: View ?= null
    private var categoryImageView: ImageView ?= null
    private var categoryEditText: EditText ?= null
    private var createButton: Button ?= null

    private var imageFile: File?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        myView = inflater.inflate(R.layout.fragment_restaurant_category, container, false)

        categoryImageView = myView?.findViewById(R.id.category_image_view)
        categoryEditText = myView?.findViewById(R.id.category_edit_text)
        createButton = myView?.findViewById(R.id.add_category_button)

        categoryImageView?.setOnClickListener {
            selectImage()
        }

        createButton?.setOnClickListener {
            createCategory()
        }
        return myView
    }

    private fun createCategory() {
        val categoryName = categoryEditText?.text.toString()

        if(imageFile != null) {
            val requestFile = imageFile?.asRequestBody("image/*".toMediaTypeOrNull())
            val image =
                requestFile?.let { MultipartBody.Part.createFormData("image", imageFile?.name, it) }

            val category = Category(name = categoryName)
            val requestBody = category.toJson().toRequestBody("text/plain".toMediaTypeOrNull())

            if (image != null) {

                val call = RetrofitService.Builder()
                    .getRetrofit(context)
                    .getApi()
                    .createCategory(requestBody, image)

                call.enqueue(object : Callback<ResponseHttp> {
                    override fun onResponse(
                        call: Call<ResponseHttp>,
                        response: Response<ResponseHttp>
                    ) {
                        if (response.isSuccessful) {
                            val user: User? = response.deserializeToObject(User::class.java)

                            if (user != null) {
                                val sesionToken = user.session_token ?: return
                                SessionManager.getInstance(requireContext())
                                    .setTokenSession(sesionToken)
                                SessionManager.getInstance(requireContext())
                                    .setRememberSession(true)
                                Toast.makeText(requireContext(), "todo chido", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "no regresa usuario",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    override fun onFailure(p0: Call<ResponseHttp>, p1: Throwable) {
                        Toast.makeText(requireContext(), "hubo un error $p1", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            } else {
                Toast.makeText(requireContext(), "Selecciona una imagen", Toast.LENGTH_SHORT).show()
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
                categoryImageView?.setImageURI(fileUri)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(requireContext(), "Tarea se cancelo", Toast.LENGTH_LONG).show()
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
}