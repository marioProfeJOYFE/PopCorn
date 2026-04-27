package com.mrh.popcorn.data.repository

import com.mrh.popcorn.data.model.Movie
import com.mrh.popcorn.data.model.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository {

    private val apiKey = "c95b8cf52728e540d04d74700f011aca"

    private val posterURL = "https://image.tmdb.org/t/p/w500"

    suspend fun getPopularMovies(): List<Movie> {
        return withContext(Dispatchers.IO){
            try {

                val response = RetrofitClient.api.getPopularMovies(apiKey)
                val moviesResponse = response.results

                val domainMovies: List<Movie> = moviesResponse.map{ networkObj ->
                    Movie(
                        id = networkObj.id,
                        title = networkObj.title,
                        posterUrl = posterURL + networkObj.posterPath,
                        rating = networkObj.rating
                    )
                }

                domainMovies

            } catch (e: Exception){
                emptyList()
            }
        }
    }

}