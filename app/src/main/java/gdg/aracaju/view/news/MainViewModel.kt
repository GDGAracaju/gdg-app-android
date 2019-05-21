package gdg.aracaju.view.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gdg.aracaju.domain.model.Event
import gdg.aracaju.domain.service.EventsService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class MainViewModel(private val service: EventsService) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Main

    private val emmitNews: MutableLiveData<List<Event>> = MutableLiveData()
    private val emmitLoading: MutableLiveData<Boolean> = MutableLiveData()

    private val jobs = ArrayList<Job>()

    private infix fun ArrayList<Job>.add(job: Job) {
        this.add(job)
    }

    fun listToEvents(): LiveData<List<Event>> = emmitNews

    fun showEvents() {
        jobs add launch {
            emmitLoading.value = true
            emmitNews.value = service.fetch()
            emmitLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.cancel() }
    }
}