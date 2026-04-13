package com.mrh.popcorn.data.viewmodel

import androidx.lifecycle.ViewModel
import com.mrh.popcorn.data.model.Movie
import com.mrh.popcorn.data.repository.MovieMockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())

    val popularMovies = _popularMovies.asStateFlow()

    init {
        val movieMockRepository = MovieMockRepository()
        _popularMovies.value = movieMockRepository.getPopularMovies()
    }

    fun toggleFavourite(movieId: Int) {
        _popularMovies.value = _popularMovies.value.map { movie ->
            if (movie.id == movieId) movie.copy(isFavourite = !movie.isFavourite)
            else movie
        }
    }

}