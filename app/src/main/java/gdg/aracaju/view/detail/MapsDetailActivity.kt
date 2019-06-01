package gdg.aracaju.view.detail

import android.content.Context
import android.content.Intent
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
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        intent?.apply {
            val presentation = (extras?.get(ID) as? LocationPresentation)
            presentation?.let {
                val latLng = LatLng(it.location.latitude.toDouble(), it.location.longitude.toDouble())
                with(googleMap) {
                    addMarker(MarkerOptions().position(latLng).title(it.title).anchor(0.5f, 0.5f))
                    uiSettings.isMyLocationButtonEnabled = true
                    uiSettings.isCompassEnabled = true
                    animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            latLng,
                            ZOOM_LEVEL_BUILDING
                        )
                    )
                }
            }
        }
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
        private const val ID = "detail_map"

        fun newInstance(context: Context, location: LocationPresentation): Intent {
            return Intent(context, MapsDetailActivity::class.java).apply {
                putExtra(ID, location)
            }
        }

        private const val ZOOM_LEVEL_BUILDING = 20f
    }
}
