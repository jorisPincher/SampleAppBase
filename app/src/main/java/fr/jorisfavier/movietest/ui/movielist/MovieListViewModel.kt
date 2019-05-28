package fr.jorisfavier.movietest.ui.movielist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.jorisfavier.movietest.api.MovieDbService
import fr.jorisfavier.movietest.manager.IMovieManager
import fr.jorisfavier.movietest.models.Movie
import fr.jorisfavier.movietest.models.dto.ChangedMovieDTO
import fr.jorisfavier.movietest.models.dto.MovieDetailDTO
import fr.jorisfavier.movietest.models.dto.ResultDTO
import retrofit2.Call
import retrofit2.Response


class MovieListViewModel : ViewModel() {
    lateinit var movieManager: IMovieManager
    private val _movies: MutableLiveData<ArrayList<Movie>> = MutableLiveData()
    private val _error: MutableLiveData<String?> = MutableLiveData()

    init {
        _movies.postValue(ArrayList())
    }

    fun getMovies(): LiveData<ArrayList<Movie>> = _movies
    fun getError(): LiveData<String?> = _error

    fun loadMovies(){
        _movies.value?.clear()
        movieManager.getLastChangedMovies(0,
            object: retrofit2.Callback<ResultDTO<ChangedMovieDTO>>{
                override fun onFailure(call: Call<ResultDTO<ChangedMovieDTO>>, t: Throwable) {
                    _error.postValue("An error occured")
                }

                override fun onResponse(call: Call<ResultDTO<ChangedMovieDTO>>, response: Response<ResultDTO<ChangedMovieDTO>>) {
                    //To prevent overloading we limit to 20 calls
                    response.body()?.results?.filter { !it.adult }?.take(20)?.forEach { loadMovieDetails(it.id) }
                }
            })
    }

    private fun loadMovieDetails(id: Int) {
        movieManager.getMovieDetail(id,
            object: retrofit2.Callback<MovieDetailDTO> {
                override fun onFailure(call: Call<MovieDetailDTO>, t: Throwable) {
                    Log.w(MovieListViewModel::class.java.simpleName,"Unable to deserialize object with id : $id")
                }

                override fun onResponse(call: Call<MovieDetailDTO>, response: Response<MovieDetailDTO>) {
                    val movieDTO = response.body()
                    //Prevent issue with GsonConverter
                    // (https://bytes.babbel.com/en/articles/2018-05-25-kotlin-gson-nullability.html)
                    if (movieDTO == null
                        || movieDTO.genres == null
                        || movieDTO.language == null
                        || movieDTO.overview == null
                    || movieDTO.id == null
                    || movieDTO.posterPath == null
                    || movieDTO.releaseDate == null
                    || movieDTO.title == null) {
                        return
                    }
                    _movies.value?.add(Movie.from(response.body()!!))
                    _movies.postValue(_movies.value)
                }
            })
    }
}
