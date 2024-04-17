package com.example.delivery.Activities.select_roles.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery.Activities.profiles.Client.Home.view.ClientHomeActivity
import com.example.delivery.Activities.profiles.Delivery.home.view.DeliveryActivity
import com.example.delivery.Activities.profiles.Restaurant.home.view.RestaurantActivity
import com.example.delivery.Activities.select_roles.view.adapter.RolesAdapter
import com.example.delivery.R
import com.example.delivery.data.models.Rol
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.Activities.select_roles.view.adapter.RolClickListener
import com.example.delivery.utils.SessionManager
import com.example.delivery.utils.enums.Roles
import com.google.gson.Gson

class SelectRolesActivity : AppCompatActivity(), SelectRoleContract.View, RolClickListener {

    private var rolesRecyclerView: RecyclerView ?= null
    private var user: User?= null
    private var roles = ArrayList<Rol>()
    private lateinit var adapter: RolesAdapter
    private lateinit var presenter: SelectRolePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_roles)

        presenter = SelectRolePresenter(this)

        rolesRecyclerView = findViewById(R.id.roles_recycler_view)
        rolesRecyclerView?.layoutManager = LinearLayoutManager(this)

        presenter.getUserFromSession()

        adapter = RolesAdapter(this, roles, this)
        rolesRecyclerView?.adapter = adapter
    }
    override fun updateUserFromSession() {
        user = SessionManager.getInstance(this).getDataFromPreferences("user", User::class.java)
        val rolesList = Gson().fromJson(user?.roles.toString(), Array<Rol>::class.java)
        roles.addAll(rolesList)
    }

    override fun onRolClicked(rol: Rol) {
        when(rol.name) {
            Roles.restaurant.label -> {
                SessionManager.getInstance(this).setUserRol(Roles.restaurant.label)
                val i = Intent(this, RestaurantActivity::class.java)
                startActivity(i)
            }
            Roles.client.label -> {
                SessionManager.getInstance(this).setUserRol(Roles.client.label)
                val i = Intent(this, ClientHomeActivity::class.java)
                startActivity(i)
            }
            Roles.delivery.label -> {
                SessionManager.getInstance(this).setUserRol(Roles.delivery.label)
                val i = Intent(this, DeliveryActivity::class.java)
                startActivity(i)
            }
        }
    }
}