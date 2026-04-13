package com.mrh.popcorn.data.repository

import com.mrh.popcorn.data.model.Movie
import com.mrh.popcorn.data.model.MovieDetail
import com.mrh.popcorn.data.remote.RetrofitClient
import com.mrh.popcorn.data.remote.TmdbApiService

class MovieRepository(
    private val apiService: TmdbApiService = RetrofitClient.apiService
) {

    suspend fun getPopularMovies(page: Int = 1): List<Movie> {
        val response = apiService.getPopularMovies(page = page)
        return response.results.map { tmdbMovie ->
            Movie(
                id = tmdbMovie.id,
                title = tmdbMovie.title,
                posterUrl = tmdbMovie.posterPath
                    ?.let { "${RetrofitClient.IMAGE_BASE_URL}$it" }
                    ?: "",
                rating = tmdbMovie.voteAverage
            )
        }
    }

    suspend fun searchMovies(query: String, page: Int = 1): List<Movie> {
        val response = apiService.searchMovies(query = query, page = page)
        return response.results.map { tmdbMovie ->
            Movie(
                id = tmdbMovie.id,
                title = tmdbMovie.title,
                posterUrl = tmdbMovie.posterPath
                    ?.let { "${RetrofitClient.IMAGE_BASE_URL}$it" }
                    ?: "",
                rating = tmdbMovie.voteAverage
            )
        }
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetail {
        val response = apiService.getMovieDetails(movieId)
        return MovieDetail(
            id = response.id,
            title = response.title,
            posterPath = "https://image.tmdb.org/t/p/w500${response.posterPath}",
            backdropPath = "https://image.tmdb.org/t/p/w780${response.backdropPath}",
            voteAverage = response.voteAverage,
            overview = response.overview,
            releaseDate = response.releaseDate,
            runtime = response.runtime,
            genres = response.genres.map { it.name },
            tagline = response.tagline
        )
    }
}
