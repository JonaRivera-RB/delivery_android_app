package com.example.delivery.Activities.select_roles.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery.R
import com.example.delivery.data.models.Rol

class RolesAdapter(
): RecyclerView.Adapter<RolesAdapter.ViewHolder>() {

    private var roles = ArrayList<Rol>()
    private lateinit var context: Context

    fun addAll(roles: ArrayList<Rol>) {
        roles.forEach {
            add(it)
        }
    }

    private fun add(rol: Rol) {
        roles.add(rol)
        notifyItemInserted(roles.size - 1)
    }

    fun removeAll() {
        roles = ArrayList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RolesAdapter.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rol_card_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RolesAdapter.ViewHolder, position: Int) {
        val item = roles[position]

        holder.rolTextview.text = item.name
    }

    override fun getItemCount(): Int {
        return roles.size
    }

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val rolTextview: TextView = view.findViewById(R.id.rol_text_view)
        val rolImageView: ImageView = view.findViewById(R.id.rol_image_view)
    }
}