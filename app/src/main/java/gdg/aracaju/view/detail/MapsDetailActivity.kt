package gdg.aracaju.view.detail

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.chip.Chip
import gdg.aracaju.domain.model.Location
import gdg.aracaju.news.R

class MapsDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_detail)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
//        viewModel.listToLocation().observe(this, Observer {
//            val location = LatLng(it.location.latitude.toDouble(), it.location.longitude.toDouble())
//            googleMap.apply {
//                addMarker(MarkerOptions().position(location).title(it.title).anchor(0.5f, 0.5f))
//                uiSettings.isMyLocationButtonEnabled = true
//                uiSettings.isCompassEnabled = true
//                animateCamera(CameraUpdateFactory.newLatLngZoom(location, DetailActivity.ZOOM_LEVEL_BUILDING))
//            }
//        })

        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun setupTransportApp(location: Location) {
        AppTransportManager.retrieveListOfTransportApps(this, location).forEach {
            val chip = Chip(this)
            chip.text = it.nameApp

            chip.setTextColor(getColor(R.color.colorPrimary))
            chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
            chip.setOnClickListener { _ ->
                startActivity(it.intent)
            }

            //  appsTransport.addView(chip)

        }
    }

    companion object {
        private val ZOOM_LEVEL_BUILDING = 20f
    }
}
