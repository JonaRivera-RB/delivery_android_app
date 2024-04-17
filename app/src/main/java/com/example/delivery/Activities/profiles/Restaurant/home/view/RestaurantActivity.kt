package com.example.delivery.Activities.profiles.Restaurant.home.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.delivery.Activities.profiles.Client.fragments.ClientProfileFragment
import com.example.delivery.Activities.profiles.Restaurant.home.view.fragments.RestaurantCategoryFragment
import com.example.delivery.Activities.profiles.Restaurant.home.view.fragments.RestaurantOrdersFragment
import com.example.delivery.Activities.profiles.Restaurant.home.view.fragments.RestaurantProductFragment
import com.example.delivery.R
import com.example.delivery.utils.objects.FragmentUtils
import com.example.delivery.utils.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView

class RestaurantActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPref? = null

    var bottomNavigation: BottomNavigationView? = null
    val transaction = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        FragmentUtils.openFragment(transaction, RestaurantOrdersFragment(), R.id.container)
        sharedPreferences = SharedPref(this)

        bottomNavigation = findViewById(R.id.bottom_navigation)

        bottomNavigation?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> {
                    FragmentUtils.openFragment(transaction, RestaurantOrdersFragment(), R.id.container)
                    true
                }

                R.id.item_category -> {
                    FragmentUtils.openFragment(transaction, RestaurantCategoryFragment(), R.id.container)
                    true
                }

                R.id.item_product -> {
                    FragmentUtils.openFragment(transaction, RestaurantProductFragment(), R.id.container)
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
