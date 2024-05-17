package com.example.delivery.Activities.profiles.Client.ShoppingBag.view

import ShoppingBagAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery.Activities.profiles.Client.address.list.AddressListClientActivity
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.entities.Product
import com.example.delivery.R
import com.example.delivery.utils.SharedPref
import com.google.gson.Gson

class ShoppingBagClientActivity : AppCompatActivity() {

    var recyclerViewShoppingBag: RecyclerView? = null
    var textViewTotal: TextView? = null
    var buttonNext: Button? = null
    var toolbar: Toolbar? = null

    var adapter: ShoppingBagAdapter? = null
    var sharedPref: SharedPref? = null
    var gson = Gson()
    var selectedProducts = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_bag_client)

        sharedPref = SharedPref(this)

        recyclerViewShoppingBag = findViewById(R.id.recyclerview_shopping_bag)
        textViewTotal = findViewById(R.id.textview_total)
        buttonNext = findViewById(R.id.btn_next)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        toolbar?.title = "Tu orden"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerViewShoppingBag?.layoutManager = LinearLayoutManager(this)

        getProductsFromSharedPref()

        buttonNext?.setOnClickListener { goToAddressList() }
    }

    private fun goToAddressList() {
        val intent = Intent(this, AddressListClientActivity::class.java)
        startActivity(intent)
    }
    fun setTotal(total: Double) {
        textViewTotal?.text = "${total}$"
    }
    private fun getProductsFromSharedPref() {
        if(!sharedPref?.getData("order").isNullOrBlank()) {
            // val type = object : TypeToken<ArrayList<Product>>() {}.type
            val products = Gson().fromJson(sharedPref?.getData("order"), Array<Product>::class.java)

            selectedProducts.addAll(products)
            adapter = ShoppingBagAdapter(this, selectedProducts)
            recyclerViewShoppingBag?.adapter = adapter
        }
    }
}