package gdg.aracaju.view.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gdg.aracaju.domain.model.Event
import gdg.aracaju.domain.model.ScreenState
import gdg.aracaju.domain.service.EventsService
import gdg.aracaju.view.add
import gdg.aracaju.view.getOrError
import gdg.aracaju.view.login.AuthControl
import gdg.aracaju.view.news.CallToAction.Contribute
import gdg.aracaju.view.news.CallToAction.Logout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class DashboardViewModel(
    private val service: EventsService,
    private val auth: AuthControl
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Main

    private val emmitState: MutableLiveData<ScreenState<List<Event>>> = MutableLiveData()
    private val stateForAction: MutableLiveData<CallToAction> = MutableLiveData()

    private val jobs = ArrayList<Job>()

    val actions: MutableLiveData<Action> = MutableLiveData()

    fun listenToEvents(): LiveData<ScreenState<List<Event>>> = emmitState
    fun listenToActions(): LiveData<CallToAction> = stateForAction

    fun showEvents() {
        jobs add launch {
            emmitState.value = ScreenState.Loading
            emmitState.value = getOrError { service.fetch() }
        }
    }

    fun passAction(action: Action) {
        when (action) {
            is Action.Logout -> {
                auth.logout()
                stateForAction.value = Logout
            }
            is Action.Contribute -> {
                stateForAction.value =
                    Contribute("https://github.com/GDGAracaju/gdg-app-android/blob/master/README.md")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.cancel() }
    }
}