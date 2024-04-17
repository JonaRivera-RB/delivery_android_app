package com.example.delivery.utils.objects

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object FragmentUtils {

    fun openFragment(fragmentManager: FragmentManager, fragment: Fragment, containerId: Int) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    inline fun <reified T : Fragment> openFragment(fragmentManager: FragmentManager, containerId: Int) {
        val fragment = T::class.java.newInstance()
        openFragment(fragmentManager, fragment, containerId)
    }
}