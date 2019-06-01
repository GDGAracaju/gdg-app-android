package gdg.aracaju.view.detail

import gdg.aracaju.domain.model.Location
import java.io.Serializable

data class LocationPresentation(
    val title: String,
    val location: Location
) : Serializable

data class Header(
    val title: String,
    val date: String,
    val time: String,
    val address: String,
    val description: String
)