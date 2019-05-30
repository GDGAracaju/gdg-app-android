package gdg.aracaju.view.detail

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.chip.Chip
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.data.api.detail.DetailRepository
import gdg.aracaju.domain.model.Detail
import gdg.aracaju.domain.model.Location
import gdg.aracaju.domain.model.ScreenState
import gdg.aracaju.news.R
import gdg.aracaju.news.R.string.label_date
import gdg.aracaju.news.R.string.label_hour
import gdg.aracaju.view.bold
import gdg.aracaju.view.normal
import gdg.aracaju.view.plus
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_header_detail.view.*


class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private val service by lazy { DetailRepository() }

    private val viewModel by lazy {
        ViewModelProviders.of(this, DetailViewModelFactory(service)).get(DetailViewModel::class.java)
    }

    private val adapter by lazy { GroupAdapter<ViewHolder>() }
    private val manager by lazy { LinearLayoutManager(this) }

    private val id by lazy { intent?.extras?.get(ID) as? Int }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel.listenEvent().observe(this, Observer {
            manageState(it)
        })

        viewModel.retrieveDetail(id)
        setupRv()
        header.share.setOnClickListener { } //todo
        val mapFragment = map as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.listToLocation().observe(this, Observer {
            val location = LatLng(it.location.latitude.toDouble(), it.location.longitude.toDouble())
            googleMap.apply {
                addMarker(MarkerOptions().position(location).title(it.title).anchor(0.5f, 0.5f))
                uiSettings.isMyLocationButtonEnabled = true
                uiSettings.isCompassEnabled = true
                animateCamera(CameraUpdateFactory.newLatLngZoom(location, ZOOM_LEVEL_BUILDING))
            }
        })
    }

    private fun setupRv() {
        talksRv.apply {
            layoutManager = manager
        }
    }

    private fun manageState(state: ScreenState<Detail>) {
        when (state) {
            is ScreenState.Loading -> loadingStateView.isVisible = true
            is ScreenState.Content -> setupContent(state.result)
            is ScreenState.Error -> errorStateView.isVisible = true
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

            appsTransport.addView(chip)

        }
    }

    private fun setupContent(result: Detail) {
        loadingStateView.isVisible = false
        with(result) {

            header.eventTitle.text = title

            val schedule = bold("${getString(label_date)} ") +
                    normal("$date ") + bold("${getString(label_hour)} ") + normal(time)

            header.scheduleEvent.text = schedule

            header.addressEvent.text = address

            talks.forEach {
                adapter.add(TalkEntry(it) {})
            }.also {
                talksRv.adapter = adapter
            }

            setupTransportApp(location)
        }
    }

    companion object {
        private const val ID = "id"

        fun newInstance(context: Context, key: Int): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(ID, key)
            }
        }

        private val ZOOM_LEVEL_BUILDING = 20f
    }
}