package com.mrh.popcorn.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrh.popcorn.data.model.MovieDetail
import com.mrh.popcorn.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieId: Int
): ViewModel() {

    private val repository = MovieRepository()

    private val _movie = MutableStateFlow<MovieDetail?>(null)
    val movie = _movie.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init{
        loadMovie()
    }

    private fun loadMovie() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _movie.value = repository.getMovieDetails(movieId)
            }catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }



}