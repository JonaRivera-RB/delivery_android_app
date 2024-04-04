package com.example.delivery.Activities.Splash

interface SplashContract {

    interface Presenter {
        fun checkForNewVersion()
    }

    interface View {
        fun showUpdateAppView()
        fun showNextActivity()
    }
}