package com.example.delivery.Activities.profiles.Client.productDetail.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.entities.Product
import com.example.delivery.R
import com.example.delivery.data.models.Rol
import com.example.delivery.utils.SessionManager
import com.example.delivery.utils.SharedPref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.time.times

class ProductDetailClientActivity : AppCompatActivity() {

    var product: Product ?= null

    lateinit var imageSlider: ImageSlider
    lateinit var nameTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var priceTextView: TextView
    lateinit var counterTextView: TextView
    lateinit var addImageView: ImageView
    lateinit var removeImageView: ImageView
    lateinit var addButton: Button

    private var counter = 1
    private var productPrice = 0.0

    var sharedPref: SharedPref ?= null
    var selectedProducts = ArrayList<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail_client)
        sharedPref = SharedPref(this)

        product = Gson().fromJson(intent.getStringExtra("product"), Product::class.java)

        imageSlider = findViewById(R.id.imageslider)
        nameTextView = findViewById(R.id.textview_name)
        descriptionTextView = findViewById(R.id.textview_description)
        priceTextView = findViewById(R.id.textview_price)
        counterTextView = findViewById(R.id.textview_counter)
        addImageView = findViewById(R.id.imageview_add)
        removeImageView = findViewById(R.id.imageview_remove)
        addButton = findViewById(R.id.btn_add_product)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(product?.image1, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(product?.image2, ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(product?.image3, ScaleTypes.CENTER_CROP))

        imageSlider.setImageList(imageList)

        nameTextView.text = product?.name
        descriptionTextView.text = product?.description
        priceTextView.text = "${product?.price}"

        addImageView.setOnClickListener { addItem() }
        removeImageView.setOnClickListener { removeItem() }
        addButton.setOnClickListener {addToBag() }

        getProductsFromSharedPref()
    }

    private fun addItem() {
        counter += 1
        productPrice = (product?.price ?: 0.0) * counter
        product?.quantity = counter
        counterTextView.text = "${product?.quantity}"
        priceTextView.text = "$${productPrice}"
    }

    private fun removeItem() {
        if (counter > 1) {
            counter -= 1
            productPrice = (product?.price ?: 0.0) * counter
            product?.quantity = counter
            counterTextView.text = "${product?.quantity}"
            priceTextView.text = "$${productPrice}"
        }
    }

    private fun addToBag() {
        val index = product?.id?.let { getIndexOf(it) }

        if(index == -1) {
            if(product?.quantity == 0) {
                product?.quantity = 1
            }

            product?.let { selectedProducts.add(it) }
        } else {
            if (index != null) {
                selectedProducts[index].quantity = counter
            }
        }

        sharedPref?.save("order", selectedProducts)
        Toast.makeText(this, "Producto agregado", Toast.LENGTH_LONG).show()
    }

    private fun getProductsFromSharedPref() {
        if(!sharedPref?.getData("order").isNullOrBlank()) {
           // val type = object : TypeToken<ArrayList<Product>>() {}.type
            val products = Gson().fromJson(sharedPref?.getData("order"), Array<Product>::class.java)
            selectedProducts.addAll(products)
            //selectedProducts = Gson().fromJson(sharedPref?.getData("order"), type) as ArrayList<Product>

            val index = product?.id?.let { getIndexOf(it) }

            if (index != null && index != -1) {
                counter = selectedProducts[index].quantity ?: 0
                product?.quantity = selectedProducts[index].quantity
                counterTextView.text = "${product?.quantity}"

                productPrice = product?.price!! * product?.quantity!!
                priceTextView.text = "$productPrice"
            }

            for(p in selectedProducts) {
                Log.d("DATOS", "Shared pref: $p")
            }
        }
    }

    private fun getIndexOf(idProduct: String): Int {
        var pos = 0

        for(p in selectedProducts) {
            if(p.id == idProduct) {
                return pos
            }

            pos++
        }

        return -1
    }
}