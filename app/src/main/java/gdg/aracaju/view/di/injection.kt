package gdg.aracaju.view.di

import gdg.aracaju.domain.service.DetailService
import gdg.aracaju.domain.service.EventsService
import gdg.aracaju.view.detail.detail.DetailViewModel
import gdg.aracaju.view.detail.detail.DetailViewModelFactory
import gdg.aracaju.view.detail.detail.Sharer
import gdg.aracaju.view.login.AuthControl
import gdg.aracaju.view.login.AuthManager
import gdg.aracaju.view.login.LoginViewModel
import gdg.aracaju.view.login.LoginViewModelFactory
import gdg.aracaju.view.news.DashboardViewModel
import gdg.aracaju.view.news.DashboardViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val viewModule = Kodein.Module("view"){

    bind<AuthControl>() with provider {
        AuthManager(
            activity = instance(),
            service = instance()
        )
    }

    bind<DashboardViewModel>() with provider {
        val service = instance<EventsService>()
        val auth = instance<AuthControl>()
        DashboardViewModelFactory(service, auth).create(DashboardViewModel::class.java)
    }

    bind<DetailViewModel>() with provider {
        val service = instance<DetailService>()
        DetailViewModelFactory(service).create(DetailViewModel::class.java)
    }

    bind<LoginViewModel>() with provider {
        val service = instance<AuthControl>()
        LoginViewModelFactory(service).create(LoginViewModel::class.java)
    }
}