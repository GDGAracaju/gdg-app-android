package gdg.aracaju.view.detail.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gdg.aracaju.domain.service.DetailService

internal class DetailViewModelFactory(private val service: DetailService) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(service) as T
    }
}