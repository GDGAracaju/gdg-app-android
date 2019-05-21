package gdg.aracaju.view.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gdg.aracaju.domain.service.EventsService

class MainViewModelFactory(private val service: EventsService) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(service) as T
    }

}