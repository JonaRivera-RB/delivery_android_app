package com.example.delivery.Activities.profiles.Delivery.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.delivery.Activities.profiles.Delivery.fragments.DeliveryOrderStatusFragment

class DeliveryTabsPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val numberOfTabs: Int
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return numberOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        val fragment = DeliveryOrderStatusFragment()
        when (position) {
            0 -> {
                bundle.putString("status", "DESPACHADO")
            }
            1 -> {
                bundle.putString("status", "EN CAMINO")
            }
            2 -> {
                bundle.putString("status", "ENTREGADO")
            }
        }
        fragment.arguments = bundle
        return fragment
    }
}
