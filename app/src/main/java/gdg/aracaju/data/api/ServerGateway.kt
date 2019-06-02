package gdg.aracaju.data.api

import gdg.aracaju.data.api.detail.DetailResponse
import gdg.aracaju.data.api.events.EventsResponse
import gdg.aracaju.domain.model.LoginState

internal interface ServerGateway {

    suspend fun fetchEvents(): List<EventsResponse>

    suspend fun fetchDetails(id: Int): DetailResponse

    suspend fun authenticate(token: String?): LoginState

    fun logout()

    fun hasUser(): Boolean
}