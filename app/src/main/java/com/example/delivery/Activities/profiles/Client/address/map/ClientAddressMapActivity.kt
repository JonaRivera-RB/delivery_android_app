package com.example.delivery.Activities.profiles.Client.address.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.delivery.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class ClientAddressMapActivity : AppCompatActivity(), OnMapReadyCallback {

    val TAG = "ClientAddressMap"
    var googleMap: GoogleMap? = null


    val PERMISSION_ID = 42
    var fusedLocationClient: FusedLocationProviderClient? = null

    var textViewAddress: TextView? = null
    var buttonAccept: Button? = null

    var city = ""
    var country = ""
    var address = ""
    var addressLatLng: LatLng? = null

    private val locationCallback = object: LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {

            var lastLocation = locationResult.lastLocation
            Log.d("LOCALIZACION", "Callback: $lastLocation")

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_address_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        textViewAddress = findViewById(R.id.textview_address)
        buttonAccept = findViewById(R.id.btn_accept)

        buttonAccept?.setOnClickListener { goToCreateAddress() }
    }

    private fun goToCreateAddress() {
        val i = Intent()
        i.putExtra("city", city)
        i.putExtra("address", address)
        i.putExtra("country", country)
        i.putExtra("lat", addressLatLng?.latitude)
        i.putExtra("lng", addressLatLng?.longitude)
        setResult(RESULT_OK, i)
        finish() // VOLVER HACIA ATRAS
    }

    private fun onCameraMove() {

        googleMap?.setOnCameraIdleListener {

            try {

                val geocoder = Geocoder(this)
                addressLatLng = googleMap?.cameraPosition?.target
                val addressList = geocoder.getFromLocation(addressLatLng?.latitude ?: 0.0, addressLatLng?.longitude ?: 0.0, 1)
                city = addressList?.get(0)?.locality ?: "" // CIUDAD
                country = addressList?.get(0)?.countryName ?:  ""// CIUDAD
                address = addressList?.get(0)?.getAddressLine(0) ?: "" // CIUDAD

                textViewAddress?.text = "$address $city"

            } catch (e: Exception) {
                Log.d(TAG, "Error: ${e.message}")
            }

        }

    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        getLastLocation()
        onCameraMove()
    }

    private fun getLastLocation() {
        if (checkPermission()) {

            if (isLocationEnabled()) {

                fusedLocationClient?.lastLocation?.addOnCompleteListener { task ->

                    var location = task.result

                    if (location == null) {
                        requestNewLocationData()
                    }
                    else {
                        googleMap?.moveCamera(
                            CameraUpdateFactory.newCameraPosition(
                                CameraPosition.builder()
                                    .target(LatLng(location.latitude, location.longitude))
                                    .zoom(15f) // Ajusta este valor según tus necesidades
                                    .build()
                            )
                        )
                    }

                }

            }
            else {
                Toast.makeText(this, "Habilita la localizacion", Toast.LENGTH_LONG).show()
                val i = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(i)
            }

        }
        else {
            requestPermissions()
        }
    }

    private fun requestNewLocationData() {
        val locationRequest = LocationRequest.create().apply {
            interval = 100
            fastestInterval = 50
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

    }



    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }
}