package com.mrh.popcorn.data.model.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarFavorito(movie: MovieLocal)

    @Delete
    suspend fun eliminarFavorito(movie: MovieLocal)

    @Query("SELECT * FROM favourite_movies")
    fun getFavoritos(): Flow<List<MovieLocal>>

    @Query("SELECT EXISTS(SELECT * FROM favourite_movies WHERE id = :movieId)")
    fun esFavorita(movieId: Int): Flow<Boolean>

}