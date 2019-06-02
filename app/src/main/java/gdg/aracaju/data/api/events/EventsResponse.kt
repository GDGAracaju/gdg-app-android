package gdg.aracaju.data.api.events

import androidx.annotation.Keep

@Keep
internal data class EventsResponse(
    val date: String,
    val id_details: Int,
    val img: String,
    val name: String,
    val time: TimeResponse

) {
    constructor() : this("", 0, "", "", TimeResponse())
}

@Keep
internal data class TimeResponse(
    val start: String = "",
    val end: String = ""
)