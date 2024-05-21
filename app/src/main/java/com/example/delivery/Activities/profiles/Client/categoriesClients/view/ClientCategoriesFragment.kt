package com.example.delivery.Activities.profiles.Client.categoriesClients.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery.Activities.Injection
import com.example.delivery.Activities.profiles.Client.ShoppingBag.view.ShoppingBagClientActivity
import com.example.delivery.Activities.profiles.Client.categoriesClients.view.adapters.CategoriesAdapter
import com.example.delivery.Activities.profiles.Client.categoriesClients.view.adapters.CategoryClickListener
import com.example.delivery.Activities.profiles.Client.products.view.ProductListClientActivity
import com.example.delivery.Activities.profiles.Restaurant.home.entities.Category
import com.example.delivery.R
import com.example.delivery.utils.objects.LoadingView

class ClientCategoriesFragment : Fragment(), CategoryClickListener, ClientCategoriesContract.View {

    var myView: View? = null
    var categoriesRecyclerView: RecyclerView ?= null
    var toolbar: Toolbar ?= null


    var adapter: CategoriesAdapter ?= null
    var categories = ArrayList<Category>()

    private lateinit var presenter: ClientCategoriesPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        presenter = ClientCategoriesPresenter(Injection.restaurantProductRepository(requireContext()), this)
        myView = inflater.inflate(R.layout.fragment_client_categories, container, false)

        setHasOptionsMenu(true)

        toolbar = myView?.findViewById(R.id.toolbar)
        toolbar?.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        toolbar?.title = "Categorias"

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        categoriesRecyclerView = myView?.findViewById(R.id.categories_recyclerview)
        categoriesRecyclerView?.layoutManager = LinearLayoutManager(requireContext())

        presenter.getCategories()

        return myView
    }

    override fun onCategoryClicked(category: Category) {
        val intent = Intent(context, ProductListClientActivity::class.java)
        intent.putExtra("idCategory", category.id)

        startActivity(intent)
    }

    override fun showLoader(show: Boolean) {
        if (show) LoadingView.showDialog(requireActivity(), "Registrando usuario...")
        else LoadingView.hideDialog()
    }

    override fun showErrorView(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    override fun updateCategories(categories: ArrayList<Category>) {
        adapter = CategoriesAdapter(requireActivity(), categories, this@ClientCategoriesFragment)
        categoriesRecyclerView?.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_shopping_bag, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.item_shopping_bag) {
            goToShoppingCart()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun goToShoppingCart() {
        val intent = Intent(requireContext(), ShoppingBagClientActivity::class.java)
        startActivity(intent)
    }
}