package gdg.aracaju.domain.service

import gdg.aracaju.domain.model.Event

internal interface EventsService {

    suspend fun fetch(): List<Event>
}