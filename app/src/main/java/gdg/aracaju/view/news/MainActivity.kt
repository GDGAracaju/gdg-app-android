package gdg.aracaju.view.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.data.api.events.EventsRepository
import gdg.aracaju.domain.ScreenState
import gdg.aracaju.news.R
import kotlinx.android.synthetic.main.activity_main.*

import gdg.aracaju.domain.MyView

internal class MainActivity : AppCompatActivity(), MyView {


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

        viewModel.listToEvents().observe(this, Observer { eventList ->
            adapter.clear()
            if (eventList.isEmpty()){
                getState(ScreenState.Empty)

            }else {

                eventList.forEach { event ->
                    adapter.add(NewsEntry(event))
                }
                newsList.adapter = adapter
                getState(ScreenState.Loaging)
            }
        })
    }

    private fun setupViews() {
        newsList.apply {
            layoutManager = manager
        }
    }

    override fun showList() {
        newsList.visibility = View.VISIBLE
        emptyStateText.visibility = View.INVISIBLE
    }

    override fun showEmptyState() {
        newsList.visibility = View.INVISIBLE
        emptyStateText.visibility = View.VISIBLE

    }

    override fun showErrorState(message: String) {
        Snackbar.make(
            toolBarMainActivity, message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun showLoadingView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getState(state: ScreenState) {
        when (state) {
            is ScreenState.Empty -> {
                showEmptyState()
            }
            is ScreenState.Loaging -> {
                showList()
            }
            is ScreenState.Error -> {
                showErrorState("Error: Failed to fetch information")
            }
        }
    }
}
