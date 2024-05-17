package com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.view
import RetrofitService
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.delivery.Activities.Injection
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.entities.Product
import com.example.delivery.Activities.profiles.Restaurant.home.entities.Category
import com.example.delivery.R
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.utils.objects.LoadingView
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RestaurantProductFragment : Fragment(), RestaurantProductContract.View {

    var myView: View? = null
    var editTextName: EditText? = null
    var editTextDescription: EditText? = null
    var editTextPrice: EditText? = null
    var imageViewProduct1: ImageView? = null
    var imageViewProduct2: ImageView? = null
    var imageViewProduct3: ImageView? = null
    var buttonCreate: Button? = null
    var spinnerCategories: Spinner? = null

    var imageFile1: File? = null
    var imageFile2: File? = null
    var imageFile3: File? = null

    var idCategory = ""

    var categories = ArrayList<Category>()
    private lateinit var presenter: RestaurantProductPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = RestaurantProductPresenter(Injection.restaurantProductRepository(requireContext()), this)
        myView = inflater.inflate(R.layout.fragment_restaurant_product, container, false)

        editTextName = myView?.findViewById(R.id.edittext_name)
        editTextDescription = myView?.findViewById(R.id.edittext_description)
        editTextPrice = myView?.findViewById(R.id.edittext_price)
        imageViewProduct1 = myView?.findViewById(R.id.imageview_image1)
        imageViewProduct2 = myView?.findViewById(R.id.imageview_image2)
        imageViewProduct3 = myView?.findViewById(R.id.imageview_image3)
        buttonCreate = myView?.findViewById(R.id.btn_create)
        spinnerCategories = myView?.findViewById(R.id.spinner_categories)
        spinnerCategories = myView?.findViewById(R.id.spinner_categories)

        buttonCreate?.setOnClickListener { createProduct() }
        imageViewProduct1?.setOnClickListener { selectImage(101) }
        imageViewProduct2?.setOnClickListener { selectImage(102) }
        imageViewProduct3?.setOnClickListener { selectImage(103) }

        presenter.getCategories()

        return myView
    }

    private fun createProduct() {
        val name = editTextName?.text.toString()
        val description = editTextDescription?.text.toString()
        val priceText = editTextPrice?.text.toString()

        var files = ArrayList<File?>()
        if (isValidForm(name, description, priceText)) {

            val product = Product(
                name = name,
                description = description,
                price = priceText.toDouble(),
                id_category = idCategory
            )

            files.add(imageFile1)
            files.add(imageFile2)
            files.add(imageFile3)


            val images = arrayOfNulls<MultipartBody.Part>(files.size)

            for(i in 0 until files.size) {
                val requestFile = files[i]?.asRequestBody("image/*".toMediaTypeOrNull())
                images[i] = requestFile?.let {
                    MultipartBody.Part.createFormData("image", files[i]?.name, it)
                }
            }

            val mediaType = "text/plain".toMediaTypeOrNull()
            val requestBody = product.toJson().toRequestBody(mediaType)

            val call = RetrofitService.Builder()
                .getRetrofit(requireContext())
                .getApi()
                .createProduct(images, requestBody)


            call.enqueue(object : Callback<ResponseHttp> {
                override fun onResponse(p0: Call<ResponseHttp>, p1: Response<ResponseHttp>) {

                }

                override fun onFailure(p0: Call<ResponseHttp>, p1: Throwable) {

                }

            })
        }

    }

    private fun isValidForm(name: String, description: String, price: String): Boolean {

        if (name.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Ingresa el nombre del producto", Toast.LENGTH_SHORT).show()
            return false
        }
        if (description.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Ingresa la descripcion del producto", Toast.LENGTH_SHORT).show()
            return false
        }
        if (price.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Ingresa el precio del producto", Toast.LENGTH_SHORT).show()
            return false
        }
        if (imageFile1 == null) {
            Toast.makeText(requireContext(), "Selecciona la imagen 1", Toast.LENGTH_SHORT).show()
            return false
        }
        if (imageFile2 == null) {
            Toast.makeText(requireContext(), "Selecciona la imagen 2", Toast.LENGTH_SHORT).show()
            return false
        }
        if (imageFile3 == null) {
            Toast.makeText(requireContext(), "Selecciona la imagen 3", Toast.LENGTH_SHORT).show()
            return false
        }
        if (idCategory.isNullOrBlank()) {
            Toast.makeText(requireContext(), "Selecciona la categoria del producto", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            val fileUri = data?.data

            if (requestCode == 101) {
                imageFile1 = File(fileUri?.path) // EL ARCHIVO QUE VAMOS A GUARDAR COMO IMAGEN EN EL SERVIDOR
                imageViewProduct1?.setImageURI(fileUri)
            }
            else if (requestCode == 102) {
                imageFile2 = File(fileUri?.path) // EL ARCHIVO QUE VAMOS A GUARDAR COMO IMAGEN EN EL SERVIDOR
                imageViewProduct2?.setImageURI(fileUri)
            }

            else if (requestCode == 103) {
                imageFile3 = File(fileUri?.path) // EL ARCHIVO QUE VAMOS A GUARDAR COMO IMAGEN EN EL SERVIDOR
                imageViewProduct3?.setImageURI(fileUri)
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectImage(requestCode: Int) {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start(requestCode)
    }

    override fun showLoader(show: Boolean) {
        if (show) LoadingView.showDialog(requireActivity(), "Registrando usuario...")
        else LoadingView.hideDialog()
    }

    override fun showErrorView(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    override fun updateCategories(categories: ArrayList<Category>) {
        val arrayAdapter = ArrayAdapter<Category>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories)
            spinnerCategories?.adapter = arrayAdapter
            spinnerCategories?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    idCategory = categories[position].id.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }
}