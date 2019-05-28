package gdg.aracaju.data.api.detail

import gdg.aracaju.data.api.ApiBuilder
import gdg.aracaju.data.api.ServerGateway
import gdg.aracaju.domain.model.Detail
import gdg.aracaju.domain.model.EventType
import gdg.aracaju.domain.model.Location
import gdg.aracaju.domain.model.Talk
import gdg.aracaju.domain.service.DetailService

internal class DetailRepository(private val api: ServerGateway = ApiBuilder) : DetailService {

    override suspend fun fetchDetail(id: String): Detail {
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
            time = time,
            title = title,
            talks = talks.map { Talk(author = it.author, title = it.title) },
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