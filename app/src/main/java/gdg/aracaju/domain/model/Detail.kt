package gdg.aracaju.domain.model

import java.io.Serializable

data class Detail(
    val address: String,
    val date: String,
    val description: String,
    val location: Location,
    val subscriptionUrl: String,
    val time: Time,
    val title: String,
    val talks: List<Talk>,
    val type: EventType
)

data class Location(
    val latitude: String,
    val longitude: String
) : Serializable

data class Talk(
    val author: String?,
    val title: String,
    val time: String
)

data class Time(
    val start: String,
    val end: String
)

enum class EventType {
    Talk,
    Dojo,
    Unknown
}