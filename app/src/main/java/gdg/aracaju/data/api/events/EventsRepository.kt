package gdg.aracaju.data.api.events

import gdg.aracaju.data.api.ApiBuilder
import gdg.aracaju.data.api.ServerGateway
import gdg.aracaju.domain.model.Event
import gdg.aracaju.domain.service.EventsService

internal class EventsRepository(private val api: ServerGateway = ApiBuilder) : EventsService {

    override suspend fun fetch(): List<Event> {

        return api.fetchEvents().map { it.toDomain() }
    }

    private fun EventsResponse.toDomain(): Event = with(this) {
        Event(
            nameEvent = name,
            date = date,
            imgUrl = img,
            idDetails = id_details,
            time = time
        )
    }
}