package gdg.aracaju.view.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.data.api.events.EventsRepository
import gdg.aracaju.domain.model.Event
import gdg.aracaju.domain.model.ScreenState
import gdg.aracaju.news.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_state_layout.*

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
        })
    }

    private fun getState(state: ScreenState<List<Event>>) {

        when (state) {
            is ScreenState.Content -> showContent(state.result)
            is ScreenState.Loading -> showLoadingView()
            is ScreenState.Error -> showErrorState()
        }
    }

    private fun setupViews() {
        eventsRv.apply {
            layoutManager = manager
        }
    }

    private fun showList() {
        loadingStateView.isVisible = false
        emptyStateView.isVisible = false
        errorStateView.isVisible = false
        eventsRv.isVisible = true
    }

    private fun showEmptyState() {
        loadingStateView.isVisible = false
        errorStateView.isVisible = false
        eventsRv.isVisible = false
        emptyStateView.isVisible = true
    }

    private fun showErrorState() {
        loadingStateView.isVisible = false
        eventsRv.isVisible = false
        emptyStateView.isVisible = false
        errorStateView.isVisible = true
        buttonErrorTryAgain.setOnClickListener {
            viewModel.showEvents()
        }
    }

    private fun showLoadingView() {
        adapter.clear()
        emptyStateView.isVisible = false
        errorStateView.isVisible = false
        eventsRv.isVisible = false
        loadingStateView.isVisible = true
    }

    private fun showContent(events: List<Event>?) {
        events?.let {
            when (events.isEmpty()) {
                false -> {
                    it
                        .forEach { event -> adapter.add(NewsEntry(event)) }
                        .also {
                            eventsRv.adapter = adapter
                            showList()
                        }
                }
                true -> showEmptyState()
            }
        } ?: showEmptyState()
    }
}