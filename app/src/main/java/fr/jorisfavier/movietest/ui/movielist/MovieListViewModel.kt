package fr.jorisfavier.movietest.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jorisfavier.movietest.models.Movie
import fr.jorisfavier.movietest.repository.MovieRepository
import kotlinx.coroutines.launch


class MovieListViewModel(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _movies = MutableLiveData(emptyList<Movie>())
    val movies: LiveData<List<Movie>> = _movies

    private val _error: MutableLiveData<String?> = MutableLiveData()
    val error: LiveData<String?> = _error

    fun loadMovies() {
        viewModelScope.launch {
            _movies.value = movieRepository.getLastChangedMovies(0)
        }
    }


}
