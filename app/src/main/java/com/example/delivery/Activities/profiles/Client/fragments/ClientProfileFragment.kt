package com.example.delivery.Activities.profiles.Client.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.delivery.Activities.Login.LoginView.LoginActivity
import com.example.delivery.Activities.profile.profile_view.view.ClientUpdateActivity
import com.example.delivery.R
import com.example.delivery.Activities.register.entities.User
import com.example.delivery.utils.AppConstants
import com.example.delivery.utils.SessionManager
import com.example.delivery.utils.SharedPref
import de.hdodenhof.circleimageview.CircleImageView

class ClientProfileFragment : Fragment() {

    private var sharedPreferences: SharedPref?= null

    private lateinit var myView: View

    private lateinit var buttonLogout: Button
    private lateinit var updateProfileButton: Button
    private lateinit var selectRolButton: Button
    lateinit var userImageView: CircleImageView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView

    private var user: User?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        myView = inflater.inflate(R.layout.fragment_client_profile, container, false)
        buttonLogout = myView.findViewById(R.id.logout_button)
        selectRolButton = myView.findViewById(R.id.select_rol_button)
        updateProfileButton = myView.findViewById(R.id.edit_profile_button)
        nameTextView = myView.findViewById(R.id.user_name_text_view)
        emailTextView = myView.findViewById(R.id.user_email_text_view)
        phoneTextView = myView.findViewById(R.id.user_phone_text_view)
        userImageView = myView.findViewById(R.id.user_image_view)

        getUserFromSession()

        nameTextView.text = "${user?.name} ${user?.lastname}"
        emailTextView.text = user?.email
        phoneTextView.text = user?.phone

        if(!user?.image.isNullOrBlank()) {
            Glide.with(requireContext()).load(user?.image).into(userImageView)
        }


        buttonLogout.setOnClickListener {
            logout()
        }

        updateProfileButton.setOnClickListener {
            goToUpdateProfile()
        }

        return myView
    }
    private fun getUserFromSession() {
        user = SessionManager.getInstance(requireContext()).getDataFromPreferences("user", User::class.java)
    }
    private fun logout() {
        val session = SessionManager.getInstance(requireContext())
        session.remove(AppConstants.USER_ROL)
        session.setRememberSession(false)

        sharedPreferences?.remove("user")

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
    }

    private fun goToUpdateProfile() {
        val intent = Intent(requireContext(), ClientUpdateActivity::class.java)
        startActivity(intent)
    }
}