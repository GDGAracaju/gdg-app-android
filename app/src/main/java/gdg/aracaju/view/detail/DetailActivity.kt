package gdg.aracaju.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import gdg.aracaju.data.api.detail.DetailRepository
import gdg.aracaju.domain.model.Detail
import gdg.aracaju.domain.model.ScreenState
import gdg.aracaju.news.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private val service by lazy { DetailRepository() }

    private val viewModel by lazy {
        ViewModelProviders.of(this, DetailViewModelFactory(service)).get(DetailViewModel::class.java)
    }

    private val id by lazy { intent?.extras?.get(ID) as? Int }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        viewModel.listenEvent().observe(this, Observer {
            manageState(it)
        })

        viewModel.retrieveDetail(id)

        val mapFragment = map as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun manageState(state: ScreenState<Detail>) {
        when (state) {
            is ScreenState.Loading -> loadingStateView.isVisible = true
            is ScreenState.Content -> setupContent(state.result)
            is ScreenState.Error -> errorStateView.isVisible = true
        }
    }

    private fun setupContent(result: Detail) {
        loadingStateView.isVisible = false
        with(result) {
            eventTitle.text = title
            scheduleEvent.text = "Data $date  Horário: $time"
            addressEvent.text = address
        }
    }

    companion object {
        private const val ID = "id"

        fun newInstance(context: Context, key: Int): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(ID, key)
            }
        }
    }
}