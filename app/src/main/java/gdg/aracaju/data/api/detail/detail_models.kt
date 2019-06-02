package gdg.aracaju.data.api.detail

import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties
import gdg.aracaju.data.api.events.TimeResponse
import java.util.*

@IgnoreExtraProperties
@Keep
internal data class DetailResponse(
    val id: String = "",
    val address: String = "",
    val date: String = "",
    val description: String = "",
    val location: Location = Location(),
    val subscription: String = "",
    val time: TimeResponse = TimeResponse(),
    val title: String = "",
    val talks: ArrayList<TalkResponse?> = ArrayList(),
    val type: String = ""
)

@Keep
internal data class Location(val lat: String = "", val long: String = "")

@Keep
internal data class TalkResponse(
    val author: String? = "",
    val title: String = "",
    val time: String = ""
)