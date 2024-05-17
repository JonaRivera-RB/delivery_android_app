package com.example.delivery.Activities.profiles.Client.address.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.actions.FloatAction
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.delivery.Activities.profiles.Client.address.create.AddressCreateClientActivity
import com.example.delivery.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddressListClientActivity : AppCompatActivity() {

    var createAddressFab: FloatingActionButton ?= null
    var toolbar: Toolbar ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list_client)

        createAddressFab = findViewById(R.id.fab_address_create)
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitleTextColor(ContextCompat.getColor(this,R.color.black))
        toolbar?.title = "Mis direcciones"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        createAddressFab?.setOnClickListener { goToCreateAddress() }
    }

    private fun goToCreateAddress() {
        val intent = Intent(this, AddressCreateClientActivity::class.java)
        startActivity(intent)
    }
}