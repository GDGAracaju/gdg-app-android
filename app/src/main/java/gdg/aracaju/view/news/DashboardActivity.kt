package gdg.aracaju.view.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.data.api.events.EventsRepository
import gdg.aracaju.data.api.login.AuthRepository
import gdg.aracaju.domain.model.Event
import gdg.aracaju.domain.model.ScreenState
import gdg.aracaju.news.R
import gdg.aracaju.view.detail.detail.DetailActivity
import gdg.aracaju.view.login.AuthManager
import gdg.aracaju.view.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_state_layout.*

internal class DashboardActivity : AppCompatActivity() {

    private val eventService by lazy { EventsRepository() }
    private val authService by lazy { AuthRepository() }
    private val auth by lazy { AuthManager(this, authService) }

    private val viewModel by lazy {
        ViewModelProviders
            .of(this, DashboardViewModelFactory(eventService, auth))
            .get(DashboardViewModel::class.java)
    }
    private val adapter by lazy { GroupAdapter<ViewHolder>() }
    private val manager by lazy { LinearLayoutManager(this) }
    private val bottomNavDrawerFragment by lazy { BottomNavigationDrawerFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        setupBottomBar()
        viewModel.showEvents()

        viewModel.listenToEvents().observe(this, Observer { state -> handleState(state) })
        viewModel.listenToActions().observe(this, Observer { state -> handleStateToActions(state) })
        viewModel.actions.observe(this, Observer { viewModel.passAction(it) })
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast

                Log.d("TOOKEN", token)
            })
    }

    private fun handleState(state: ScreenState<List<Event>>) {

        when (state) {
            is ScreenState.Content -> showContent(state.result)
            is ScreenState.Loading -> showLoadingView()
            is ScreenState.Error -> {
                state.e.printStackTrace()
                showErrorState()
            }
        }
    }

    private fun handleStateToActions(state: CallToAction) {
        when (state) {
            CallToAction.Logout -> {
                startActivity(Intent(this, LoginActivity::class.java))
                bottomNavDrawerFragment.dismiss()
                finish()
            }

            is CallToAction.Contribute -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(state.url)))
                bottomNavDrawerFragment.dismiss()
            }

            is CallToAction.Site -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(state.url)))
                bottomNavDrawerFragment.dismiss()
            }
        }
    }

    private fun setupViews() {
        eventsRv.apply {
            layoutManager = manager
        }
    }

    private fun setupBottomBar() {
        setSupportActionBar(bar)
        bar.setNavigationOnClickListener {
            bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            bottomNavDrawerFragment.setListener(viewModel.actions)
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
        adapter.clear()
        events?.let {
            when (events.isEmpty()) {
                false -> {
                    it.forEach { event ->
                        adapter.add(DashboardEntry(event) {
                            startActivity(DetailActivity.newInstance(this, event.id))
                        })
                    }.also {
                        eventsRv.adapter = adapter
                        showList()
                    }
                }
                true -> showEmptyState()
            }
        } ?: showEmptyState()
    }
}