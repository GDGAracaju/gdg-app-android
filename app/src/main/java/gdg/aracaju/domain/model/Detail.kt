package gdg.aracaju.domain.model

data class Detail(
    val address: String,
    val date: String,
    val description: String,
    val location: Location,
    val subscriptionUrl: String,
    val time: String,
    val title: String,
    val talks: List<Talk>,
    val type: EventType
)

data class Location(
    val latitude: String,
    val longitude: String
)

data class Talk(
    val author: String,
    val title: String
)

enum class EventType {
    Talk,
    Dojo,
    Unknown
}