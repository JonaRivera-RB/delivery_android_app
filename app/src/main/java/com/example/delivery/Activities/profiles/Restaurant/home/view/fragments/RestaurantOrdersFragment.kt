package com.example.delivery.Activities.profiles.Restaurant.home.view.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.delivery.Activities.profiles.Restaurant.adapters.RestaurantTabPagerAdapter
import com.example.delivery.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RestaurantOrdersFragment : Fragment() {

    private var myView: View? = null

    private var viewpager: ViewPager2? = null
    private var tabLayout: TabLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_restaurant_orders, container, false)

        viewpager = myView?.findViewById(R.id.viewpager)
        tabLayout = myView?.findViewById(R.id.tab_layout)

        tabLayout?.setSelectedTabIndicatorColor(Color.BLACK)
        tabLayout?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        tabLayout?.tabTextColors = ContextCompat.getColorStateList(requireContext(), R.color.black)
        tabLayout?.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout?.isInlineLabel = true

        val numberOfTabs = 4

        val adapter = RestaurantTabPagerAdapter(childFragmentManager, lifecycle, numberOfTabs)
        viewpager?.adapter = adapter
        viewpager?.isUserInputEnabled = true

        TabLayoutMediator(tabLayout!!, viewpager!!) { tab, position ->
            when (position) {
                0 -> tab.text = "PAGADO"
                1 -> tab.text = "DESPACHADO"
                2 -> tab.text = "EN CAMINO"
                3 -> tab.text = "ENTREGADO"
            }
        }.attach()

        return myView
    }
}
