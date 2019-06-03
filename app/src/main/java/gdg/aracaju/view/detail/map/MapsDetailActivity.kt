package gdg.aracaju.view.detail.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.domain.model.Location
import gdg.aracaju.news.R
import gdg.aracaju.view.detail.detail.LocationPresentation
import kotlinx.android.synthetic.main.activity_maps_detail.*

class MapsDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private val bottomSheetBehavior by lazy { BottomSheetBehavior.from(bottomSheet) }
    private val adapter by lazy { GroupAdapter<ViewHolder>() }
    private val manager by lazy { LinearLayoutManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_detail)
        configMap()
        toolbarMap.setNavigationOnClickListener { finish() }
        bottomSheet.isVisible = false
        configBottomSheet()
    }

    private fun configMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun configBottomSheet() {
        bottomSheetBehavior.apply {
            isHideable = false
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
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
                    animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL_BUILDING))
                }
                setupTransportApp(presentation.location)
            }
        }
    }

    private fun setupTransportApp(location: Location) {
        val cabsApps = AppTransportManager.retrieveListOfTransportApps(this, location)
        if (cabsApps.isNotEmpty()) {
            adapter.add(CabTitleEntry())
            cabsApps.forEach {
                adapter.add(CabItemEntry(it) { startActivity(it.action) })
            }
            cabs.layoutManager = manager
            cabs.adapter = adapter
            bottomSheet.isVisible = true
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    companion object {
        private const val ID = "detail_map"

        fun newInstance(context: Context, location: LocationPresentation): Intent {
            return Intent(context, MapsDetailActivity::class.java).apply {
                putExtra(ID, location)
            }
        }

        private const val ZOOM_LEVEL_BUILDING = 17f
    }
}
