package ru.akbirov.weather.app.screens.main.tabs.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createCircleAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import ru.akbirov.weather.R
import ru.akbirov.weather.app.model.weather.entities.Coord
import ru.akbirov.weather.app.screens.base.BaseFragment
import ru.akbirov.weather.databinding.FragmentMapBinding

class MapFragment : BaseFragment(R.layout.fragment_map) {

    override val viewModel by viewModels<MapViewModel>()

    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMapBinding.bind(view)

        mapView = binding.mapView
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)

        mapView.getMapboxMap().addOnMapClickListener {
            viewModel.onSelect(it.longitude(), it.latitude())
            return@addOnMapClickListener true
        }

        binding.saveLocationButton.setOnClickListener {
            viewModel.onSave()
        }

        viewModel.selectedCoordinate.observe(viewLifecycleOwner) {
            it?.let { coord ->
                addAnnotationToMap(coord.lon.toDouble(), coord.lat.toDouble())

            }
            binding.saveLocationButton.isEnabled = it != null
        }

        viewModel.coordinateIsInit.observe(viewLifecycleOwner) {
            it.get()?.let { coord ->
                setupMap(coord)
            }
        }
    }

    private fun setupMap(coord: Coord) {
        val cameraPosition = CameraOptions.Builder()
            .center(Point.fromLngLat(coord.lon.toDouble(), coord.lat.toDouble()))
            .build()

        addCurrentCoordinateAnnotationToMap(coord.lon.toDouble(), coord.lat.toDouble())

        mapView.getMapboxMap().setCamera(cameraPosition)
    }

    private fun addAnnotationToMap(lon: Double, lat: Double) {
        mapView.annotations.cleanup()

        val annotationApi = mapView.annotations
        val circleAnnotationManager = annotationApi.createCircleAnnotationManager()

        val circleAnnotationOptions = createCircleAnnotationOptions(
            lon,
            lat,
            ResourcesCompat.getColor(resources, R.color.primaryColor, null),
            ResourcesCompat.getColor(
                resources,
                R.color.secondaryColor,
                null
            )
        )

        circleAnnotationManager.create(circleAnnotationOptions)
    }

    private fun addCurrentCoordinateAnnotationToMap(lon: Double, lat: Double) {
        val annotationApi = mapView.annotations
        val circleAnnotationManager = annotationApi.createCircleAnnotationManager()

        val circleAnnotationOptions = createCircleAnnotationOptions(
            lon,
            lat,
            ResourcesCompat.getColor(resources, R.color.indianRed, null),
            ResourcesCompat.getColor(
                resources,
                R.color.secondaryColor,
                null
            )
        )

        circleAnnotationManager.create(circleAnnotationOptions)
    }

    private fun createCircleAnnotationOptions(
        lon: Double,
        lat: Double,
        @ColorInt circleColor: Int,
        @ColorInt circleStrokeColor: Int
    ): CircleAnnotationOptions {
        return CircleAnnotationOptions()
            .withPoint(Point.fromLngLat(lon, lat))
            .withCircleRadius(8.0)
            .withCircleColor(circleColor)
            .withCircleStrokeWidth(2.0)
            .withCircleStrokeColor(circleStrokeColor)
    }

    @SuppressLint("Lifecycle")
    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    @SuppressLint("Lifecycle")
    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    @SuppressLint("Lifecycle")
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    @SuppressLint("Lifecycle")
    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}