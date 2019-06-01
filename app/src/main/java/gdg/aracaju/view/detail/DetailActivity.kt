package gdg.aracaju.view.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events.ALL_DAY
import android.provider.CalendarContract.Events.TITLE
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.data.api.detail.DetailRepository
import gdg.aracaju.domain.Date
import gdg.aracaju.domain.model.Detail
import gdg.aracaju.domain.model.ScreenState
import gdg.aracaju.news.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

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
        setSupportActionBar(bar)
        toolbarDetail.setNavigationOnClickListener { finish() }
        viewModel.listenEvent().observe(this, Observer {
            manageState(it)
        })

        viewModel.retrieveDetail(id)
        setupRv()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.map -> {
                viewModel.listToLocation().value?.let {
                    startActivity(MapsDetailActivity.newInstance(this, it))
                }
            }
            R.id.share -> {

            }

            R.id.calendar -> {
                viewModel.listenEvent().value.let {
                    if (it is ScreenState.Content) {
                        val intent = Intent(Intent.ACTION_EDIT)
                        intent.type = "vnd.android.cursor.item/event"
                        intent.putExtra(TITLE, it.result.title)
                        intent.putExtra(
                            CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                            Date.getTimeInMillis("2019-07-16", "09:00:00")
                        )
                        intent.putExtra(
                            CalendarContract.EXTRA_EVENT_END_TIME,
                            Date.getTimeInMillis("2019-07-16", "17:00")
                        )
                        intent.putExtra(ALL_DAY, false)
                        intent.resolveActivity(packageManager)?.let {
                            startActivity(intent)
                        }
                    }
                }
            }
        }
        return true
    }

    private fun setupRv() {
        talksRv.apply { layoutManager = manager }
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
            val header = Header(title = title, date = date, time = time, address = address, description = description)

            adapter.add(HeaderEntry(header))

            if (talks.isNotEmpty()) {
                adapter.add(LabelEntry())
                talks.forEach { adapter.add(TalkEntry(it) {}) }
                    .also { talksRv.adapter = adapter }
            }

            fab.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(this.subscriptionUrl))
                startActivity(browserIntent)
            }
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