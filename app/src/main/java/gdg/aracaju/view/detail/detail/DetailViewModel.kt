package gdg.aracaju.view.detail.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gdg.aracaju.domain.model.Detail
import gdg.aracaju.domain.model.ScreenState
import gdg.aracaju.domain.service.DetailService
import gdg.aracaju.view.add
import gdg.aracaju.view.getOrErrorBy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel(private val service: DetailService) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val event: MutableLiveData<ScreenState<Detail>> = MutableLiveData()
    private val location: MutableLiveData<LocationPresentation> = MutableLiveData()

    private val jobs = ArrayList<Job>()

    fun listenEvent(): LiveData<ScreenState<Detail>> = event
    fun listenToLocation(): LiveData<LocationPresentation> = location

    fun retrieveDetail(id: Int?) {
        jobs add launch {
            event.value = ScreenState.Loading
            when (id != null) {
                true -> getOrErrorBy(
                    block = { service.fetchDetail(id) },
                    onError = { event.value = ScreenState.Error(it) },
                    onSuccess = {
                        event.value = ScreenState.Content(it)
                        location.value = LocationPresentation(
                            it.title, it.location
                        )
                    }
                )
                false -> event.value = ScreenState.Error(Throwable())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.cancel() }
    }
}