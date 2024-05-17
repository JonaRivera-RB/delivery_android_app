package com.example.delivery.Activities.profiles.Client.products.view

import RetrofitService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery.Activities.profiles.Client.products.adapters.ProductsAdapter
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.entities.Product
import com.example.delivery.R
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class ProductListClientActivity : AppCompatActivity() {

    lateinit var recyclerViewProduct: RecyclerView
    lateinit var adapter: ProductsAdapter

    var product: ArrayList<Product> = ArrayList()

    var idCategory: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list_client)

        idCategory = intent.getStringExtra("idCategory")

        recyclerViewProduct = findViewById(R.id.recyclerview_products)
        recyclerViewProduct.layoutManager = GridLayoutManager(this, 2)

        getProducts()
    }

    private fun getProducts() {
        val call = idCategory?.let {
            RetrofitService.Builder()
                .getRetrofit(this)
                .getApi()
                .getAllProductsByCategory(it)
        }
        call?.enqueue(object : Callback<ArrayList<Product>> {
            override fun onResponse(
                p0: Call<ArrayList<Product>>,
                response: Response<ArrayList<Product>>,
            ) {
                if(response.body() != null) {
                    product = response.body()!!
                    adapter = ProductsAdapter(this@ProductListClientActivity, product)
                    recyclerViewProduct.adapter = adapter
                }
            }

            override fun onFailure(p0: Call<ArrayList<Product>>, p1: Throwable) {
            Toast.makeText(this@ProductListClientActivity, "hubo un error", Toast.LENGTH_LONG).show()
            }
        })
    }
}