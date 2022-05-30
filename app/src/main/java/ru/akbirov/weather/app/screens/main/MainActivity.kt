package ru.akbirov.weather.app.screens.main

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import ru.akbirov.weather.R
import ru.akbirov.weather.app.Singletons

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var cancellationToken: CancellationTokenSource

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        ::onGetLocationPermission
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        Singletons.init(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        cancellationToken = CancellationTokenSource()

        viewModel.currentCoordinate.observe(this) {
            if (it == null) {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        cancellationToken.cancel()
    }

    private fun onGetLocationPermission(grantResult: Map<String, Boolean>) {
        if (grantResult.entries.all { it.value }) {
            requestLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationToken.token
        ).addOnSuccessListener { location: Location? ->
            location?.let { viewModel.saveCoordinates(location.longitude, location.latitude) }
        }
    }
}