package com.example.delivery.Activities.profiles.Client.Home.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.delivery.Activities.profiles.Client.fragments.ClientCategoriesFragment
import com.example.delivery.Activities.profiles.Client.fragments.ClientOrdersFragment
import com.example.delivery.Activities.profiles.Client.fragments.ClientProfileFragment
import com.example.delivery.R
import com.example.delivery.utils.objects.FragmentUtils
import com.example.delivery.utils.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView

class ClientHomeActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPref ?= null

    var bottomNavigation: BottomNavigationView? = null
    val transaction = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_home)

        FragmentUtils.openFragment(transaction, ClientCategoriesFragment(), R.id.container)
        sharedPreferences = SharedPref(this)

        bottomNavigation = findViewById(R.id.bottom_navigation)

        bottomNavigation?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> {
                    FragmentUtils.openFragment(transaction, ClientCategoriesFragment(), R.id.container)
                    true
                }
                R.id.item_orders -> {
                    FragmentUtils.openFragment(transaction, ClientOrdersFragment(), R.id.container)
                    true
                }
                R.id.item_profile_client -> {
                    FragmentUtils.openFragment(transaction, ClientProfileFragment(), R.id.container)
                    true
                }

                else -> false
            }
        }
    }
}