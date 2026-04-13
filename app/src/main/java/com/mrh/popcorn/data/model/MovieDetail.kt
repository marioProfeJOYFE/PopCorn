package com.mrh.popcorn.data.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val runtime: Int?,
    val genres: List<String>, // Simplificado a solo los nombres
    val tagline: String?
)
