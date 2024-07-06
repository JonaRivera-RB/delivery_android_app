package com.example.delivery.Activities.profiles.Delivery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.delivery.R
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery.Activities.profiles.Client.orders.models.Order
import com.example.delivery.Activities.profiles.Delivery.adapters.OrdersDeliveryAdapter
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.utils.SessionManager
import com.example.delivery.utils.objects.LoadingView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeliveryOrderStatusFragment : Fragment() {

    var myView: View? = null
    var user: User? = null

    var recyclerViewOrders: RecyclerView? = null
    var adapter: OrdersDeliveryAdapter? = null

    var status = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_delivery_order_status, container, false)

        status = arguments?.getString("status")!!

        getUserFromSession()

        recyclerViewOrders = myView?.findViewById(R.id.recyclerview_orders)
        recyclerViewOrders?.layoutManager = LinearLayoutManager(requireContext())

        getOrders()

        return myView
    }


    private fun getOrders() {
        val call = user?.id?.let {
            RetrofitService.Builder()
                .getRetrofit(requireContext())
                .getApi()
                .getOrdersByDeliveryAndStatus(it, status)
        }

        call?.enqueue(object: Callback<ArrayList<Order>> {
            override fun onResponse(p0: Call<ArrayList<Order>>, p1: Response<ArrayList<Order>>) {
                if (p1.body() != null) {
                    val orders = p1.body()
                    adapter = OrdersDeliveryAdapter(requireActivity(), orders!!)
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