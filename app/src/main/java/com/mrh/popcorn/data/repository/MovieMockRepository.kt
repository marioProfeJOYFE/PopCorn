package com.mrh.popcorn.data.repository

import android.util.Log
import com.mrh.popcorn.data.model.Movie

/**
 *  Clase MovieMockRepository
 *  Devuelve peliculas hardcodeadas hasta tener datos reales
 */
class MovieMockRepository {

    fun getPopularMovies(): List<Movie> {
        val movies = mutableListOf(
            Movie(1, "Inception", "/poster_inception.jpg", 8.8, true),
            Movie(2, "Interstellar", "/poster_interstellar.jpg", 8.6),
            Movie(title = "Matrix", id = 3, posterUrl = "/poster_matrix.jpg", rating = 8.0),
            Movie(4, "Dune: Part Two", "/poster_dune2.jpg", 8.9),
            Movie(4, "Dune: Part Two", "/poster_dune2.jpg", 8.9),
            Movie(4, "Dune: Part Two", "/poster_dune2.jpg", 8.9)
        )

        movies.apply {
            add(
                Movie(4, "Dune: Part Two", "/poster_dune2.jpg", 8.9)

            )
        }

        return movies
    }

    fun processSelectedMovie(movieId: Int?){
        movieId.let {
            val allMovies = getPopularMovies()

            val selectedMovie = allMovies.find { movie ->
                movie.id == movieId
            }

            Log.d("ResultadoPelicula", selectedMovie?.title ?: "")
        } ?: run {
            Log.e("ResultadoPelicula", "El ID seleccionado es nulo")
        }
    }


}












