package com.example.delivery.Activities.select_roles.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.delivery.Activities.Client.Home.ClientHomeActivity
import com.example.delivery.R
import com.example.delivery.data.models.Rol
import com.example.delivery.utils.SharedPref

class RolesAdapter(val context: Activity, val roles: ArrayList<Rol>): RecyclerView.Adapter<RolesAdapter.RolesViewHolder>() {

    val sharedPref = SharedPref(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RolesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rol_card_view, parent, false)
        return RolesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return roles.size
    }

    override fun onBindViewHolder(holder: RolesViewHolder, position: Int) {

        val rol = roles[position] // CADA ROL

        holder.textViewRol.text = rol.name
        Glide.with(context).load(rol.imagen).into(holder.imageViewRol)

        holder.itemView.setOnClickListener { goToRol(rol) }
    }

    private fun goToRol(rol: Rol) {
        when (rol.name) {
            "RESTAURANTE" -> {
                sharedPref.save("rol", "RESTAURANTE")
                val i = Intent(context, ClientHomeActivity::class.java)
                context.startActivity(i)
                //val i = Intent(context, RestaurantHomeActivity::class.java)
                //context.startActivity(i)
            }
            "CLIENTE" -> {
                sharedPref.save("rol", "CLIENTE")
                val i = Intent(context, ClientHomeActivity::class.java)
                context.startActivity(i)
            }
            "REPARTIDOR" -> {
                sharedPref.save("rol", "REPARTIDOR")
                val i = Intent(context, ClientHomeActivity::class.java)
                context.startActivity(i)
                //val i = Intent(context, DeliveryHomeActivity::class.java)
                //context.startActivity(i)
            }
        }
    }

    class RolesViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val textViewRol: TextView
        val imageViewRol: ImageView

        init {
            textViewRol = view.findViewById(R.id.rol_text_view)
            imageViewRol = view.findViewById(R.id.rol_image_view)
        }
    }
}