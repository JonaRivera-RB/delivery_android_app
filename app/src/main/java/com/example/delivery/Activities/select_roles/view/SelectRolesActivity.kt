package com.example.delivery.Activities.select_roles.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery.Activities.select_roles.view.adapter.RolesAdapter
import com.example.delivery.R
import com.example.delivery.data.models.Rol
import com.example.delivery.data.models.User
import com.example.delivery.utils.SessionManager
import com.example.delivery.utils.SharedPref
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SelectRolesActivity : AppCompatActivity() {

    private var rolesRecyclerView: RecyclerView ?= null
    private var user: User ?= null
    private var roles = ArrayList<Rol>()
    private lateinit var adapter: RolesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_roles)

        rolesRecyclerView = findViewById(R.id.roles_recycler_view)
        rolesRecyclerView?.layoutManager = LinearLayoutManager(this)

        getUserFromSession()

        adapter = RolesAdapter(this, roles)
        rolesRecyclerView?.adapter = adapter
    }

    private fun getUserFromSession() {
        user = SessionManager.getInstance(this).getDataFromPreferences("user", User::class.java)
        val rolesList = Gson().fromJson(user?.roles.toString(), Array<Rol>::class.java)
        roles.addAll(rolesList)
    }
}