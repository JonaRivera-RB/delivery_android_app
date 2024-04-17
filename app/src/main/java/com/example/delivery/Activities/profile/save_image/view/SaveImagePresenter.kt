package com.example.delivery.Activities.profile.save_image.view

import com.example.delivery.Activities.profile.save_image.data.SaveImageDataSource
import com.example.delivery.Activities.profile.save_image.data.SaveImageRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SaveImagePresenter(
    private val repository: SaveImageRepository,
    private val saveImageView: SaveImageContract.View): SaveImageContract.Presenter {
    override fun updateImage(user: RequestBody, image: MultipartBody.Part) {
        saveImageView.showLoader(true)

        repository.updateImage(user, image, object : SaveImageDataSource.SaveImageCallback {
            override fun success() {
                saveImageView.showLoader(false)
                saveImageView.showHome()
            }

            override fun error(errorMessage: String) {
                saveImageView.showLoader(false)
                saveImageView.showError(errorMessage)
            }
        })
    }
}