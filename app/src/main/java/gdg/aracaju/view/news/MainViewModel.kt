package gdg.aracaju.view.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gdg.aracaju.domain.model.Event
import gdg.aracaju.domain.model.ScreenState
import gdg.aracaju.domain.service.EventsService
import gdg.aracaju.view.add
import gdg.aracaju.view.getOrError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class MainViewModel(private val service: EventsService) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Main

    private val emmitState: MutableLiveData<ScreenState<List<Event>>> = MutableLiveData()

    private val jobs = ArrayList<Job>()

    fun listToEvents(): LiveData<ScreenState<List<Event>>> = emmitState

    fun showEvents() {
        jobs add launch {
            emmitState.value = ScreenState.Loading
            emmitState.value = getOrError { service.fetch() }
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.cancel() }
    }
}