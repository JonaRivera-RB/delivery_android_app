package com.example.delivery.Activities.profiles.Restaurant.orders

import RetrofitService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery.Activities.profiles.Client.orders.adapter.OrderProductsAdapter
import com.example.delivery.Activities.profiles.Client.orders.models.Order
import com.example.delivery.Activities.profiles.Restaurant.home.view.RestaurantActivity
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.R
import com.example.delivery.data.models.NewResponseHttp
import com.example.delivery.utils.enums.Roles
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantOrderDetailActivity : AppCompatActivity() {

    val TAG = "ClientOrdersDetail"
    var order: Order? = null
    val gson = Gson()

    var toolbar: Toolbar? = null
    var textViewClient: TextView? = null
    var textViewAddress: TextView? = null
    var textViewDate: TextView? = null
    var textViewTotal: TextView? = null
    var textViewStatus: TextView? = null
    var textViewDelivery: TextView? = null
    var textViewDeliveryAssigned: TextView? = null
    var textViewDeliveryAvailable: TextView? = null
    var recyclerViewProducts: RecyclerView? = null
    var buttonUpdate: Button? = null

    var adapter: OrderProductsAdapter? = null

    var user: User? = null

    var spinnerDeliveryMen: Spinner? = null
    var idDelivery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_order_detail)

        order = gson.fromJson(intent.getStringExtra("order"), Order::class.java)

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
        textViewDelivery = findViewById(R.id.textview_delivery)
        textViewDeliveryAssigned = findViewById(R.id.textview_delivery_assigned)
        textViewDeliveryAvailable = findViewById(R.id.textview_delivery_available)
        spinnerDeliveryMen = findViewById(R.id.spinner_delivery_men)
        buttonUpdate = findViewById(R.id.btn_update)

        recyclerViewProducts = findViewById(R.id.recyclerview_products)
        recyclerViewProducts?.layoutManager = LinearLayoutManager(this)

        adapter = OrderProductsAdapter(this, order?.products!!)
        recyclerViewProducts?.adapter = adapter

        textViewClient?.text = "${order?.client?.name} ${order?.client?.lastname}"
        textViewAddress?.text = order?.address?.address
        textViewDate?.text = "${order?.timestamp}"
        textViewStatus?.text = order?.status
        textViewDelivery?.text = "${order?.delivery?.name} ${order?.delivery?.lastname}"

        Log.d(TAG, "Orden: ${order.toString()}")

        getTotal()

        getDeliveryMen()

        if (order?.status == "PAGADO") {
            buttonUpdate?.visibility = View.VISIBLE
            textViewDeliveryAvailable?.visibility = View.VISIBLE
            spinnerDeliveryMen?.visibility = View.VISIBLE
        }

        if (order?.status != "PAGADO") {
            textViewDeliveryAssigned?.visibility = View.VISIBLE
            textViewDelivery?.visibility = View.VISIBLE
        }

        buttonUpdate?.setOnClickListener { updateOrder() }
    }

    private fun updateOrder() {
        order?.idDelivery = idDelivery

        val call = order?.let {
            RetrofitService.Builder()
                .getRetrofit(this)
                .getApi()
                .updateToDispatchedOrder(it)
        }

        call?.enqueue(object: Callback<NewResponseHttp> {
            override fun onResponse(call: Call<NewResponseHttp>, response: Response<NewResponseHttp>) {

                if (response.body() != null) {

                    if (response.body()?.success == true) {
                        Toast.makeText(this@RestaurantOrderDetailActivity, "Repartidor asignado correctamente", Toast.LENGTH_SHORT).show()
                        goToOrders()
                    }
                    else {
                        Toast.makeText(this@RestaurantOrderDetailActivity, "No se pudo asignar el repartidor", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(this@RestaurantOrderDetailActivity, "No hubo respuesta del servidor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<NewResponseHttp>, p1: Throwable) {

                Toast.makeText(this@RestaurantOrderDetailActivity, "Error: ${p1.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun goToOrders() {
        val i = Intent(this, RestaurantActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
    }

    private fun getDeliveryMen() {
        val call = RetrofitService.Builder()
            .getRetrofit(this)
            .getApi()
            .getDeliveryMen()

        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(p0: Call<ArrayList<User>>, p1: Response<ArrayList<User>>) {
                if (p1.body() != null) {
                    val deliveryMen = p1.body()

                    val arrayAdapter = ArrayAdapter<User>(
                        this@RestaurantOrderDetailActivity,
                        android.R.layout.simple_dropdown_item_1line,
                        deliveryMen!!
                    )
                    spinnerDeliveryMen?.adapter = arrayAdapter
                    spinnerDeliveryMen?.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                adapterView: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                l: Long
                            ) {
                                idDelivery =
                                    deliveryMen[position].id!! // SELECCIONANDO DEL SPINNER EL ID DEL DELIVERY
                                Log.d(TAG, "Id Delivery: $idDelivery")
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }
                        }
                }
            }

            override fun onFailure(p0: Call<ArrayList<User>>, p1: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getTotal() {
        var total = 0.0

        for (p in order?.products!!) {
            total = total + (p.price * p.quantity!!)
        }
        textViewTotal?.text = "${total}$"
    }
}