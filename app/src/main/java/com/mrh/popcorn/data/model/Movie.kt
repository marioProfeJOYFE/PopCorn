package com.mrh.popcorn.data.model

/**
 *  Clase Movie
 *
 *  @author Mario Rios Holgado
 */
data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Double,
    var isFavourite: Boolean = false
)
