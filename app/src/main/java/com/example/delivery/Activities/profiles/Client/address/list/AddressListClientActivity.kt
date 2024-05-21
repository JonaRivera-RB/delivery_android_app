package com.example.delivery.Activities.profiles.Client.address.list

import RetrofitService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery.Activities.profiles.Client.Payments.ClientPaymentFormActivity
import com.example.delivery.Activities.profiles.Client.address.create.AddressCreateClientActivity
import com.example.delivery.Activities.profiles.Client.address.create.data.AddressModel
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.R
import com.example.delivery.utils.SessionManager
import com.example.delivery.utils.SharedPref
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.optic.deliverykotlinudemy.adapters.AddressAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressListClientActivity : AppCompatActivity() {

    var createAddressFab: FloatingActionButton ?= null
    var toolbar: Toolbar ?= null

    var recyclerViewAddress: RecyclerView? = null
    var buttonNext: Button? = null
    var user: User? = null
    var adapter: AddressAdapter? = null
    var sharedPref: SharedPref? = null

    var address = ArrayList<AddressModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list_client)
        sharedPref = SharedPref(this)

        createAddressFab = findViewById(R.id.fab_address_create)
        toolbar = findViewById(R.id.toolbar)
        buttonNext = findViewById(R.id.btn_next)
        recyclerViewAddress = findViewById(R.id.recyclerview_address)

        toolbar?.setTitleTextColor(ContextCompat.getColor(this,R.color.black))
        toolbar?.title = "Mis direcciones"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        createAddressFab?.setOnClickListener { goToCreateAddress() }
        buttonNext?.setOnClickListener { getAddressFromSession() }

        recyclerViewAddress?.layoutManager = LinearLayoutManager(this)

        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        toolbar?.title = "Mis direcciones"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //getProductsFromSharedPref()
        getUserFromSession()
        getAddress()
    }

    private fun goToCreateAddress() {
        val intent = Intent(this, AddressCreateClientActivity::class.java)
        startActivity(intent)
    }

    private fun getUserFromSession() {
        user = SessionManager.getInstance(this).getDataFromPreferences("user", User::class.java)
    }

    private fun getAddress() {
        val userId = user?.id ?: return

        val call = RetrofitService.Builder()
            .getRetrofit(this)
            .getApi()
            .getAllAddressByUser(userId)

        call.enqueue(object: Callback<ArrayList<AddressModel>> {
            override fun onResponse(
                p0: Call<ArrayList<AddressModel>>,
                response: Response<ArrayList<AddressModel>>,
            ) {
                if(response.body() != null) {
                    address = response.body() as ArrayList<AddressModel>
                    adapter = AddressAdapter(this@AddressListClientActivity, address)
                    recyclerViewAddress?.adapter = adapter
                }
            }

            override fun onFailure(p0: Call<ArrayList<AddressModel>>, p1: Throwable) {
                Toast.makeText(this@AddressListClientActivity, "Error: ${p1.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
    fun resetValue(position: Int) {
        val viewHolder = recyclerViewAddress?.findViewHolderForAdapterPosition(position) // UNA DIRECCION
        val view = viewHolder?.itemView
        val imageViewCheck = view?.findViewById<ImageView>(R.id.imageview_check)
        imageViewCheck?.visibility = View.GONE
    }

    fun goToPaymentsForm() {
        val intent = Intent(this, ClientPaymentFormActivity::class.java)
        startActivity(intent)
    }

    fun getAddressFromSession() {
        if (!sharedPref?.getData("address").isNullOrBlank()) { // SI EL USUARIO ELIJIO UNA DIRECCION DE LA LISTA
            val adress = Gson().fromJson(sharedPref?.getData("address"), AddressModel::class.java)
            goToPaymentsForm()
        } else {
            Toast.makeText(this, "selecciona una direccion para continuar", Toast.LENGTH_LONG).show()
        }
    }
}