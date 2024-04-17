package com.example.delivery.Activities.select_roles.view
interface SelectRoleContract {

    interface Presenter {
        fun getUserFromSession()
    }
    interface View {
     fun updateUserFromSession()
    }
}