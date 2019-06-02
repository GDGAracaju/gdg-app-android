package gdg.aracaju.domain.model

internal data class Event(
    val id : Int,
    val nameEvent: String,
    val date: String,
    val imgUrl: String,
    val idDetails: Int,
    val time: Time
)