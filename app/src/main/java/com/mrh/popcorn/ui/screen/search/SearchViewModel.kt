package com.mrh.popcorn.ui.screen.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrh.popcorn.data.model.Movie
import com.mrh.popcorn.data.repository.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        searchJob?.cancel()
        if (newQuery.isBlank()) {
            _movies.value = emptyList()
            _error.value = null
            _isLoading.value = false
            return
        }
        searchJob = viewModelScope.launch {
            delay(300)
            _isLoading.value = true
            _error.value = null
            try {
                _movies.value = repository.searchMovies(newQuery)
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error buscando peliculas", e)
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
