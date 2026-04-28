package com.mrh.popcorn.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_movies")
data class MovieLocal(
    @PrimaryKey val id: Int,
    val title: String,
    val posterUrl: String,
    val rating: Double
)
