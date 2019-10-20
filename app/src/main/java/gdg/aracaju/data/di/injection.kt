package gdg.aracaju.data.di

import gdg.aracaju.data.api.detail.DetailRepository
import gdg.aracaju.data.api.events.EventsRepository
import gdg.aracaju.data.api.login.AuthRepository
import gdg.aracaju.domain.service.AuthService
import gdg.aracaju.domain.service.DetailService
import gdg.aracaju.domain.service.EventsService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val dataModule = Kodein.Module("data") {

    bind<DetailService>() with singleton  {
        DetailRepository()
    }

    bind<EventsService>() with singleton  {
        EventsRepository()
    }

    bind<AuthService>() with singleton  {
        AuthRepository()
    }
}