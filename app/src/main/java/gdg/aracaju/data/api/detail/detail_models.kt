package gdg.aracaju.data.api.detail


data class DetailResponse(
    val id: String,
    val address: String,
    val date: String,
    val description: String,
    val location: Location,
    val subscription: String,
    val time: String,
    val title: String,
    val talks: List<Talk>,
    val type: String
)

data class Location(
    val lat: String,
    val long: String
)

data class Talk(
    val author: String,
    val title: String
)