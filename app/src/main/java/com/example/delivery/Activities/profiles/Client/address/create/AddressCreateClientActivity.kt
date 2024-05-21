package com.example.delivery.Activities.profiles.Client.address.create

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.delivery.Activities.profiles.Client.address.create.data.AddressModel
import com.example.delivery.Activities.profiles.Client.address.map.ClientAddressMapActivity
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.R
import com.example.delivery.data.models.ResponseHttp
import com.example.delivery.utils.SessionManager
import com.example.delivery.utils.objects.LoadingView
import okhttp3.Address
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AddressCreateClientActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null
    var editTextRefPoint: EditText? = null
    var editTextAddress: EditText ?= null
    var editTextNeighbordhood: EditText ?= null
    var buttonCreateAddress: Button?= null

    var addressLat = 0.0
    var addressLng = 0.0

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if(result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val city = data?.getStringExtra("city")
            val address = data?.getStringExtra("address")
            val country = data?.getStringExtra("country")
            addressLat = data?.getDoubleExtra("lat", 0.0) ?: 0.0
            addressLng = data?.getDoubleExtra("lng", 0.0) ?: 0.0

            editTextRefPoint?.setText("$address $city")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_create_client)

        toolbar = findViewById(R.id.toolbar)
        editTextRefPoint = findViewById(R.id.edittext_ref_point)
        editTextAddress = findViewById(R.id.edittext_address)
        editTextNeighbordhood = findViewById(R.id.edittext_neighborhood)
        buttonCreateAddress = findViewById(R.id.btn_create_address)

        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        toolbar?.title = "Nueva direccion"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editTextRefPoint?.setOnClickListener { goToAddressMap() }
        buttonCreateAddress?.setOnClickListener { createAddress() }
    }

    private fun goToAddressMap() {
        val i = Intent(this, ClientAddressMapActivity::class.java)
        resultLauncher.launch(i)
    }

    private fun createAddress() {
        val address = editTextAddress?.text.toString()
        val neighborhood = editTextNeighbordhood?.text.toString()

        if (isValidForm(address, neighborhood)) {
            val userId = SessionManager.getInstance(this).getDataFromPreferences("user", User::class.java)?.id ?: return
            val address = AddressModel(null, userId, address, neighborhood, addressLat, addressLng)

            createAddress(address)
        }
    }

    private fun isValidForm(address: String, neighborhood: String): Boolean {
        if(address.isBlank()) {
            Toast.makeText(this, "Ingresar la direccion", Toast.LENGTH_SHORT).show()
            return false
        } else if (neighborhood.isBlank()) {
            Toast.makeText(this, "Ingresa la colonia", Toast.LENGTH_SHORT).show()
            return false
        } else if (addressLat == 0.0) {
            Toast.makeText(this, "Ingresa la colonia", Toast.LENGTH_SHORT).show()
            return false
        } else if (addressLng == 0.0) {
            Toast.makeText(this, "Ingresa la colonia", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun createAddress(withAddress: AddressModel) {
        showLoader(true)
        val call = RetrofitService.Builder()
            .getRetrofit(this)
            .getApi()
            .createAddress(withAddress)

        call.enqueue(object : Callback<ResponseHttp>{
            override fun onResponse(p0: Call<ResponseHttp>, p1: Response<ResponseHttp>) {
                showLoader(false)
                if(p1.body() != null) {
                    Toast.makeText(this@AddressCreateClientActivity, p1.body()?.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(p0: Call<ResponseHttp>, p1: Throwable) {
                showLoader(false)
                Toast.makeText(this@AddressCreateClientActivity, "Error ${p1.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun showLoader(show: Boolean) {
        if (show) LoadingView.showDialog(this, "Registrando usuario...")
        else LoadingView.hideDialog()
    }
}