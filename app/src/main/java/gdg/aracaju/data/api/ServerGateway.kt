package gdg.aracaju.data.api

import gdg.aracaju.data.api.events.EventsResponse

internal interface ServerGateway {

    suspend fun fetchEvents(): List<EventsResponse>
}