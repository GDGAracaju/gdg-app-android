package gdg.aracaju.data.api

import gdg.aracaju.data.api.events.EventsResponse
import kotlinx.coroutines.Deferred

internal interface ServerGateway {

    suspend fun fetchEvents(): Deferred<List<EventsResponse>>
}