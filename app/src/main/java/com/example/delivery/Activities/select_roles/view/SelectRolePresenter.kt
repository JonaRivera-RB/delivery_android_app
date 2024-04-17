package com.example.delivery.Activities.select_roles.view

class SelectRolePresenter(private val selectRolView: SelectRoleContract.View): SelectRoleContract.Presenter {
    override fun getUserFromSession() {
        selectRolView.updateUserFromSession()
    }

}