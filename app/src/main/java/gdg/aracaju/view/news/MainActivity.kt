package gdg.aracaju.view.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.data.api.events.EventsRepository
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

        viewModel.listToEvents().observe(this, Observer {
            adapter.clear()
            it.forEach {
                adapter.add(NewsEntry(it.name))
            }
            newsList.adapter = adapter
        })
    }

    private fun setupViews() {
        newsList.apply {
            layoutManager = manager
        }
    }
}
