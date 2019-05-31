package gdg.aracaju.view.detail

import gdg.aracaju.domain.model.Location

data class LocationPresentation(
    val title: String,
    val location: Location
)

data class Header(
    val title: String,
    val date: String,
    val time: String,
    val address: String,
    val description: String
)