package com.example.delivery.Activities.profiles.Restaurant.home.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery.Activities.profiles.Client.orders.models.Order
import com.example.delivery.Activities.profiles.Restaurant.adapters.OrderRestaurantAdapter
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.R
import com.example.delivery.utils.SessionManager
import com.example.delivery.utils.objects.LoadingView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantOrdersStatusFragment : Fragment() {

    var myView: View? = null
    var user: User? = null

    var recyclerViewOrders: RecyclerView? = null
    var adapter: OrderRestaurantAdapter? = null

    var status = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_restaurant_orders_status, container, false)

        status = arguments?.getString("status")!!

        getUserFromSession()

        recyclerViewOrders = myView?.findViewById(R.id.recyclerview_orders)
        recyclerViewOrders?.layoutManager = LinearLayoutManager(requireContext())

        getOrders()

        return myView
    }


    private fun getOrders() {
        val call = RetrofitService.Builder()
            .getRetrofit(requireContext())
            .getApi()
            .getOrdersByStatus(status)

        call.enqueue(object: Callback<ArrayList<Order>> {
            override fun onResponse(p0: Call<ArrayList<Order>>, p1: Response<ArrayList<Order>>) {
                if (p1.body() != null) {
                    val orders = p1.body()
                    adapter = OrderRestaurantAdapter(requireActivity(), orders!!)
                    recyclerViewOrders?.adapter = adapter
                }
            }

            override fun onFailure(p0: Call<ArrayList<Order>>, p1: Throwable) {
                Toast.makeText(requireContext(), "Error: ${p1.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun getUserFromSession() {
        user = SessionManager.getInstance(requireContext()).getDataFromPreferences("user", User::class.java)
    }

    private fun showLoader(show: Boolean) {
        if (show) LoadingView.showDialog(requireActivity(), "Cargando ordenes...")
        else LoadingView.hideDialog()
    }
}