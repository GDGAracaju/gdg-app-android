package gdg.aracaju.data.api.detail

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class DetailResponse(
    val id: String = "",
    val address: String = "",
    val date: String = "",
    val description: String = "",
    val location: Location = Location(),
    val subscription: String = "",
    val time: String = "",
    val title: String = "",
    val talks: ArrayList<Talk?> = ArrayList(),
    val type: String = ""
)

data class Location(
    val lat: String = "",
    val long: String = ""
)

data class Talk(
    val author: String = "",
    val title: String = ""
)