package gdg.aracaju.data.api.events

<<<<<<< HEAD
internal data class EventsResponse(
    val date: String,
    val id_details: Int,
    val img: String,
    val name: String,
    val time: String

) {
    constructor() : this("", 0, "", "", "")
=======
data class EventsResponse(
    val name: String
) {
    constructor() : this("")
>>>>>>> add list of events feature
}