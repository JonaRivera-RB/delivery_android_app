package com.example.delivery.Activities.profiles.Delivery.home.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.delivery.Activities.profiles.Client.fragments.ClientProfileFragment
import com.example.delivery.Activities.profiles.Delivery.home.fragments.DeliveryOrdersFragment
import com.example.delivery.R
import com.example.delivery.utils.objects.FragmentUtils
import com.example.delivery.utils.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView

class DeliveryActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPref? = null

    var bottomNavigation: BottomNavigationView? = null
    val transaction = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        FragmentUtils.openFragment(transaction, DeliveryOrdersFragment(), R.id.container)

        sharedPreferences = SharedPref(this)

        bottomNavigation = findViewById(R.id.bottom_navigation)

        bottomNavigation?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_orders -> {
                    FragmentUtils.openFragment(transaction, DeliveryOrdersFragment(), R.id.container)
                    true
                }
                R.id.item_profile -> {
                    FragmentUtils.openFragment(transaction, ClientProfileFragment(), R.id.container)
                    true
                }
                else -> false
            }
        }
    }
}
