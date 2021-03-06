package gdg.aracaju.data.api.detail

import gdg.aracaju.data.api.ApiBuilder
import gdg.aracaju.data.api.ServerGateway
import gdg.aracaju.domain.model.*
import gdg.aracaju.domain.model.Location
import gdg.aracaju.domain.model.Talk
import gdg.aracaju.domain.service.DetailService

internal class DetailRepository(private val api: ServerGateway = ApiBuilder) : DetailService {

    override suspend fun fetchDetail(id: Int): Detail {
        return api.fetchDetails(id).toDomain()
    }

    private fun DetailResponse.toDomain(): Detail {
        return Detail(
            address = address,
            date = date,
            description = description,
            location = Location(
                latitude = location.lat,
                longitude = location.long
            ),
            subscriptionUrl = subscription,
            time = Time(time.start, time.end),
            title = title,
            talks = talks.filter { it != null }.map { Talk(author = it!!.author, title = it.title, time = it.time) },
            type = mapType(type)
        )
    }

    private fun mapType(type: String): EventType {
        return when (type.toLowerCase()) {
            "event" -> EventType.Talk
            "dojo" -> EventType.Dojo
            else -> EventType.Unknown
        }
    }
}