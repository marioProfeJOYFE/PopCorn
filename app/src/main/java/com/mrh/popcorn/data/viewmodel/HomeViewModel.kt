package com.mrh.popcorn.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrh.popcorn.data.model.Movie
import com.mrh.popcorn.data.repository.MovieMockRepository
import com.mrh.popcorn.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())

    val popularMovies = _popularMovies.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        val movieRepository = MovieRepository()
        viewModelScope.launch(Dispatchers.IO){
            _isLoading.value = true
            _popularMovies.value = movieRepository.getPopularMovies()
            Log.d("HomeViewModel", "Peliculas: ${_popularMovies.value}")
            _isLoading.value = false
        }
    }

    fun toggleFavourite(movieId: Int) {
        _popularMovies.value = _popularMovies.value.map { movie ->
            if (movie.id == movieId) movie.copy(isFavourite = !movie.isFavourite)
            else movie
        }
    }

}