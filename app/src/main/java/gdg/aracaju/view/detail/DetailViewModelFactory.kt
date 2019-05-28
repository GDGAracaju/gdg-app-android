package gdg.aracaju.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gdg.aracaju.domain.service.EventsService

internal class DetailViewModelFactory(private val service: EventsService) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel() as T
    }
}