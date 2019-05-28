package fr.jorisfavier.movietest.manager.impl

import android.util.Log
import android.util.SparseArray
import fr.jorisfavier.movietest.api.MovieDbService
import fr.jorisfavier.movietest.manager.IMovieManager
import fr.jorisfavier.movietest.models.dto.ChangedMovieDTO
import fr.jorisfavier.movietest.models.dto.MovieDetailDTO
import fr.jorisfavier.movietest.models.dto.ResultDTO
import fr.jorisfavier.movietest.ui.movielist.MovieListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class MovieManager @Inject constructor(private val movieDbService: MovieDbService): IMovieManager {

    //We cached the movies in order to prevent overload
    private var cachedMovies: SparseArray<MovieDetailDTO> = SparseArray()

    override fun getLastChangedMovies(since: Int, callback: retrofit2.Callback<ResultDTO<ChangedMovieDTO>>) {
        val calendar = Calendar.getInstance()
        if (since in 0..14){
            calendar.add(Calendar.DAY_OF_YEAR, -since)
        }
        movieDbService.lastChangedMovies(calendar.time,null,null)
            .enqueue(callback)
    }

    override fun getMovieDetail(id: Int, callback: Callback<MovieDetailDTO>){
        //we first check if the detail are not in cache
        if (cachedMovies.get(id) == null){
            movieDbService.getMovieDetail(id,null)
                .enqueue(object: retrofit2.Callback<MovieDetailDTO> {
                    override fun onFailure(call: Call<MovieDetailDTO>, t: Throwable) {
                        Log.w(MovieListViewModel::class.java.simpleName,"Unable to deserialize object with id : $id")
                        callback.onFailure(call, t)
                    }

                    override fun onResponse(call: Call<MovieDetailDTO>, response: Response<MovieDetailDTO>) {
                        if (response.body() == null) {
                            return
                        }
                        callback.onResponse(call,response)
                    }
                })
        }
        else {
            callback.onResponse(null, Response.success(cachedMovies.get(id)))
        }
    }

}