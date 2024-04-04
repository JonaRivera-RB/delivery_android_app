package com.example.delivery.Activities.Splash

class SplashPresenter(private val splashView: SplashContract.View): SplashContract.Presenter {

    override fun checkForNewVersion() {
        //TODO make petition for new version
        splashView.showNextActivity()
    }
}