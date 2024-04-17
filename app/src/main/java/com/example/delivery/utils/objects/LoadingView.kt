package com.example.delivery.utils.objects

import android.app.Activity
import android.app.Dialog
import android.widget.TextView
import com.example.delivery.R

object LoadingView {

    private var dialog: Dialog? = null

    fun showDialog(activity: Activity, message: String) {
        dialog = Dialog(activity, R.style.ThemeDialogTransparent)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.loader_view)

        val loaderText = dialog?.findViewById<TextView>(R.id.loadingMessage)
        loaderText?.text = message
        dialog?.show()
    }

    fun hideDialog() {
        try {
            dialog?.dismiss()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }
}