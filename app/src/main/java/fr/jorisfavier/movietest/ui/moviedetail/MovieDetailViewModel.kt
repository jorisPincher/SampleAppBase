package fr.jorisfavier.movietest.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.jorisfavier.movietest.manager.IMovieManager
import fr.jorisfavier.movietest.models.Movie
import fr.jorisfavier.movietest.models.dto.MovieDetailDTO
import retrofit2.Call
import retrofit2.Response

class MovieDetailViewModel: ViewModel(){
    lateinit var movieManager: IMovieManager
    private var _movie = MutableLiveData<Movie>()

    fun getMovie(): LiveData<Movie> {
        return _movie
    }

    fun loadDetail(movieId: Int){
        movieManager.getMovieDetail(movieId, object : retrofit2.Callback<MovieDetailDTO>{
            override fun onFailure(call: Call<MovieDetailDTO>, t: Throwable) {
            }

            override fun onResponse(call: Call<MovieDetailDTO>, response: Response<MovieDetailDTO>) {
                if(response.body() == null){
                    return
                }
                _movie.postValue(Movie.from(response.body()!!))
            }
        })
    }


}