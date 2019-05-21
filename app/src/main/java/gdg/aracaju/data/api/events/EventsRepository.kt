package gdg.aracaju.data.api.events

import gdg.aracaju.data.api.ApiBuilder
import gdg.aracaju.data.api.ServerGateway
import gdg.aracaju.domain.service.EventsService

internal class EventsRepository(private val api: ServerGateway = ApiBuilder) : EventsService {

    override suspend fun fetch(): List<EventsResponse> {

        return api.fetchEvents().await()
    }
}