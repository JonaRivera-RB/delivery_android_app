package com.example.delivery.Activities.profiles.Client.products.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.delivery.Activities.profiles.Client.productDetail.view.ProductDetailClientActivity
import com.example.delivery.Activities.profiles.Client.products.view.ProductListClientActivity
import com.example.delivery.Activities.profiles.Restaurant.RestaurantProduct.entities.Product
import com.example.delivery.R


class ProductsAdapter(val context: Activity, val products: ArrayList<Product>): RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_product, parent, false)
        return ProductsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {

        val product = products[position] // CADA UNA DE LAS CATEGORIAS

        holder.textViewName.text = product.name
        holder.textViewPrice.text = "${product.price}$"
        Glide.with(context).load(product.image1).into(holder.imageViewProduct)


        holder.itemView.setOnClickListener { goToDetail(product) }
    }

    private fun goToDetail(product: Product) {
        val i = Intent(context, ProductDetailClientActivity::class.java)
        i.putExtra("product", product.toJson())
        context.startActivity(i)
    }

    class ProductsViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val textViewName: TextView
        val textViewPrice: TextView
        val imageViewProduct: ImageView

        init {
            textViewName = view.findViewById(R.id.textview_name)
            textViewPrice = view.findViewById(R.id.textview_price)
            imageViewProduct = view.findViewById(R.id.imageview_product)
        }

    }

}