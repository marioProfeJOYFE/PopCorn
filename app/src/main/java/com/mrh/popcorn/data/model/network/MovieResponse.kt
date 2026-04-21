package com.mrh.popcorn.data.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("results") val results: List<MovieNetwork>
)
