package fr.jorisfavier.movietest.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.jorisfavier.movietest.models.Movie
import fr.jorisfavier.movietest.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieId: Int,
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private var _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    init {
        loadDetail()
    }

    private fun loadDetail() {
        viewModelScope.launch {
            val detail = movieRepository.getMovieDetail(movieId)
            _movie.value = detail
        }
    }

}