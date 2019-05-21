package gdg.aracaju.domain.service

import gdg.aracaju.data.api.events.EventsResponse

interface EventsService {

    suspend fun fetch(): List<EventsResponse>
}