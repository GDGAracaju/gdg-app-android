package gdg.aracaju.view.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gdg.aracaju.domain.service.EventsService
import gdg.aracaju.view.login.AuthControl

internal class DashboardViewModelFactory(
    private val service: EventsService,
    private val auth: AuthControl
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DashboardViewModel(service, auth) as T
    }
}