package gdg.aracaju.view.news

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.data.api.events.EventsRepository
import gdg.aracaju.domain.model.Event
import gdg.aracaju.domain.model.ScreenState
import gdg.aracaju.news.R
import kotlinx.android.synthetic.main.activity_main.*

internal class MainActivity : AppCompatActivity() {


    private val service by lazy { EventsRepository() }
    private val viewModel by lazy {
        ViewModelProviders.of(this, MainViewModelFactory(service)).get(MainViewModel::class.java)
    }
    private val adapter by lazy { GroupAdapter<ViewHolder>() }
    private val manager by lazy { LinearLayoutManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()

        viewModel.showEvents()

        viewModel.listToEvents().observe(this, Observer { state ->
            adapter.clear()
            getState(state)


            /* */
        })
    }

    private fun setupViews() {
        newsList.apply {
            layoutManager = manager
        }
    }

    fun showList() {
        loadingStateView.visibility = View.INVISIBLE
        emptyStateText.visibility = View.INVISIBLE
        newsList.visibility = View.VISIBLE

    }

    fun showEmptyState() {
        newsList.visibility = View.INVISIBLE
        emptyStateText.visibility = View.VISIBLE

    }

    fun showErrorState(message: String) {
        Snackbar.make(
            toolBarMainActivity, message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    fun showLoadingView() {
        loadingStateView.visibility = View.VISIBLE
    }

    fun getState(state: ScreenState) {

        when (state) {
            is ScreenState.Content<*> -> {
                val myEvents = state.result as List<Event>
                if (myEvents.isEmpty()) {
                    showEmptyState()
                } else {

                    myEvents.forEach { event ->
                        adapter.add(NewsEntry(event))
                    }
                    newsList.adapter = adapter
                    getState(ScreenState.Loading)
                    showList()
                    Log.e("Sucesso", "Deu bom")
                }


            }
            is ScreenState.Loading -> {
                showLoadingView()
            }
            is ScreenState.Error -> {
                showErrorState("Error: Failed to fetch information")
            }
        }
    }
}
