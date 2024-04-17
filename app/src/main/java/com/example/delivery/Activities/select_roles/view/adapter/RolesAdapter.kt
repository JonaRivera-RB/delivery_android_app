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
import com.example.delivery.Activities.profiles.Client.Home.view.ClientHomeActivity
import com.example.delivery.Activities.profiles.Delivery.home.view.DeliveryActivity
import com.example.delivery.Activities.profiles.Restaurant.home.view.RestaurantActivity
import com.example.delivery.R
import com.example.delivery.data.models.Rol
import com.example.delivery.utils.SessionManager
import com.example.delivery.utils.enums.Roles
interface RolClickListener {
    fun onRolClicked(rol: Rol)
}
class RolesAdapter(val context: Activity, val roles: ArrayList<Rol>, val listener: RolClickListener): RecyclerView.Adapter<RolesAdapter.RolesViewHolder>() {
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

        holder.itemView.setOnClickListener {listener.onRolClicked(rol) }
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