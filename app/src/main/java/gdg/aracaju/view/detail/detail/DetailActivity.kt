package gdg.aracaju.view.detail.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.domain.Date
import gdg.aracaju.domain.model.Detail
import gdg.aracaju.domain.model.ScreenState
import gdg.aracaju.news.R
import gdg.aracaju.view.detail.map.MapsDetailActivity
import gdg.aracaju.view.kodein
import gdg.aracaju.view.viewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import kotlinx.android.synthetic.main.error_state_layout.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class DetailActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein = kodein()

    private val sharer by lazy { Sharer(this) }
    private val adapter by lazy { GroupAdapter<ViewHolder>() }
    private val manager by lazy { LinearLayoutManager(this) }
    private val calendar by lazy { CalendarEvent(this) }

    private val id by lazy { intent?.extras?.get(ID) as? Int }

    private val viewModel by viewModel<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(bar)
        toolbarDetail.setNavigationOnClickListener { finish() }
        viewModel.listenEvent().observe(this, Observer { manageState(it) })
        viewModel.retrieveDetail(id)
        setupRv()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val state = viewModel.listenEvent().value
        val detail: Detail? = if (state is ScreenState.Content) state.result else null

        when (item.itemId) {
            R.id.map -> viewModel.listenToLocation().value?.let {
                startActivity(MapsDetailActivity.newInstance(this, it))
            }
            R.id.share -> detail?.let { sharer.share(it) }
            R.id.calendar -> detail?.let { calendar.createNewEvent(it) }
        }
        return true
    }

    private fun setupRv() {
        talksRv.apply { layoutManager = manager }
    }

    private fun manageState(state: ScreenState<Detail>) {
        when (state) {
            is ScreenState.Loading -> setupLoadingState()
            is ScreenState.Content -> setupContent(state.result)
            is ScreenState.Error -> setupErrorState()
        }
    }

    private fun setupLoadingState() {
        loadingStateView.isVisible = true
        errorStateView.isVisible = false
    }

    private fun setupErrorState() {
        errorStateView.isVisible = true
        buttonErrorTryAgain.setOnClickListener {
            viewModel.retrieveDetail(id)
        }
    }

    private fun setupContent(result: Detail) {
        loadingStateView.isVisible = false
        errorStateView.isVisible = false
        with(result) {
            val header = Header(
                title = title,
                date = Date.toCurrentFormat(date), //FIXME
                time = time,
                address = address,
                description = description
            )

            adapter.add(HeaderEntry(header))

            if (talks.isNotEmpty()) {
                adapter.add(LabelEntry())
                talks
                    .forEach { adapter.add(TalkEntry(it) {}) }
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