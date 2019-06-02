package gdg.aracaju.view.detail.detail

import gdg.aracaju.domain.model.Location
import gdg.aracaju.domain.model.Time
import java.io.Serializable

data class LocationPresentation(
    val title: String,
    val location: Location
) : Serializable

data class Header(
    val title: String,
    val date: String,
    val time: Time,
    val address: String,
    val description: String
)