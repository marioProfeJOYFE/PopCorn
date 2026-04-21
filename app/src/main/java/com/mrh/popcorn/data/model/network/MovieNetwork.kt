package com.mrh.popcorn.data.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieNetwork(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    // Usamos @SerialName para enlazar el nombre feo del JSON con nuestro nombre limpio en Kotlin
    @SerialName("vote_average") val rating: Double,
    @SerialName("poster_path") val posterPath: String? // Puede ser nulo si la peli no tiene póster
)
