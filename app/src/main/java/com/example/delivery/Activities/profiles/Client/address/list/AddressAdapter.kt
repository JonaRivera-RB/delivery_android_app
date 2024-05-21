package com.optic.deliverykotlinudemy.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery.Activities.profiles.Client.address.create.data.AddressModel
import com.example.delivery.Activities.profiles.Client.address.list.AddressListClientActivity
import com.example.delivery.R
import com.example.delivery.utils.SharedPref
import com.google.gson.Gson

class AddressAdapter(val context: Activity, val address: ArrayList<AddressModel>): RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    val sharedPref = SharedPref(context)
    val gson = Gson()
    var prev = 0
    var positionAddressSession = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_address, parent, false)
        return AddressViewHolder(view)
    }

    override fun getItemCount(): Int {
        return address.size
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {

        val a = address[position] // CADA UNA DE LAS DIRECCIONES

        if (!sharedPref.getData("address").isNullOrBlank()) { // SI EL USUARIO ELIJIO UNA DIRECCION DE LA LISTA
            val adr = gson.fromJson(sharedPref.getData("address"), AddressModel::class.java)

            if (adr.id == a.id) {
                positionAddressSession = position
                holder.imageViewCheck.visibility = View.VISIBLE
            }
        }

        holder.textViewAddress.text = a.address
        holder.textViewNeighborhood.text = a.neighborhood

        holder.itemView.setOnClickListener {

            (context as AddressListClientActivity).resetValue(prev)
            (context as AddressListClientActivity).resetValue(positionAddressSession)
            prev = position // 1

            holder.imageViewCheck.visibility = View.VISIBLE
            saveAddress(a.toJson())
        }
    }

    private fun saveAddress(data: String) {
        val ad = gson.fromJson(data, AddressModel::class.java)
        sharedPref.save("address", ad)
    }

    class AddressViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val textViewAddress: TextView
        val textViewNeighborhood: TextView
        val imageViewCheck: ImageView

        init {
            textViewAddress = view.findViewById(R.id.textview_address)
            textViewNeighborhood = view.findViewById(R.id.textview_neighborhood)
            imageViewCheck = view.findViewById(R.id.imageview_check)
        }

    }

}