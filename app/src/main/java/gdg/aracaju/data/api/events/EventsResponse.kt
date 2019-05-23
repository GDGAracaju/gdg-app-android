package gdg.aracaju.data.api.events

internal data class EventsResponse(
    val date: String,
    val id_details: Int,
    val img: String,
    val name: String,
    val time: String

) {
    constructor() : this("", 0, "", "", "")
}