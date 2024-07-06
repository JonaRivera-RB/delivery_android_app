package com.example.delivery.Activities.profiles.Client.orders.views.orderDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery.Activities.profiles.Client.orders.adapter.OrderProductsAdapter
import com.example.delivery.Activities.profiles.Client.orders.models.Order
import com.example.delivery.R
import com.google.gson.Gson

class ClientOrdersDetailActivity : AppCompatActivity() {

    private val TAG = "ClientOrdersDetail"

    private var order: Order? = null
    private var toolbar: Toolbar? = null
    private var textViewClient: TextView? = null
    private var textViewAddress: TextView? = null
    private var textViewDate: TextView? = null
    private var textViewTotal: TextView? = null
    private var textViewStatus: TextView? = null
    private var recyclerViewProducts: RecyclerView? = null

    var adapter: OrderProductsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_orders_detail)

        order = Gson().fromJson(intent.getStringExtra("order"), Order::class.java)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        toolbar?.title = "Order #${order?.id}"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textViewClient = findViewById(R.id.textview_client)
        textViewAddress = findViewById(R.id.textview_address)
        textViewDate = findViewById(R.id.textview_date)
        textViewTotal = findViewById(R.id.textview_total)
        textViewStatus = findViewById(R.id.textview_status)

        recyclerViewProducts = findViewById(R.id.recyclerview_products)
        recyclerViewProducts?.layoutManager = LinearLayoutManager(this)

        adapter = OrderProductsAdapter(this, order?.products!!)
        recyclerViewProducts?.adapter = adapter

        textViewClient?.text = "${order?.client?.name} ${order?.client?.lastname}"
        textViewAddress?.text = order?.address?.address
        textViewDate?.text = "${order?.timestamp}"
        textViewStatus?.text = order?.status

        Log.d(TAG, "Orden: ${order.toString()}")

        getTotal()

    }

    private fun getTotal() {
        var total = 0.0

        for (p in order?.products!!) {
            total += (p.price * p.quantity!!)
        }
        textViewTotal?.text = "${total}$"

    }
}