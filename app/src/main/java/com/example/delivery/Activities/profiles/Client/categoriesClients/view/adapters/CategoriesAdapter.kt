package com.example.delivery.Activities.profiles.Client.categoriesClients.view.adapters

import com.example.delivery.Activities.profiles.Restaurant.home.entities.Category

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.delivery.R

interface CategoryClickListener {
    fun onCategoryClicked(category: Category)
}
class CategoriesAdapter(val context: Activity, val categories: ArrayList<Category>, val listener: CategoryClickListener): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categories_cardview, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val category = categories[position] // CADA ROL

        holder.textView.text = category.name
        Glide.with(context).load(category.image).into(holder.imageView)

        holder.itemView.setOnClickListener {listener.onCategoryClicked(category) }
    }

    class CategoryViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val textView: TextView
        val imageView: ImageView

        init {
            textView = view.findViewById(R.id.category_name_textview)
            imageView = view.findViewById(R.id.category_image_imageview)
        }
    }
}