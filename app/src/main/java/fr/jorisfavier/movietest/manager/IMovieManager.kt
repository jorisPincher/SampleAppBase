package fr.jorisfavier.movietest.manager

import fr.jorisfavier.movietest.models.dto.ChangedMovieDTO
import fr.jorisfavier.movietest.models.dto.MovieDetailDTO
import fr.jorisfavier.movietest.models.dto.ResultDTO
import retrofit2.Callback

interface IMovieManager {
    /**
     * Retrieve the last changed movies from MovieDB
     * @param since : number of days since the last change (must be between 0 and 14)
     * @param callback : callback when the movies are retrieved
     */
    fun getLastChangedMovies(since: Int, callback: retrofit2.Callback<ResultDTO<ChangedMovieDTO>>)

    /**
     * Get the movie's detailed informations
     * @param id : the movie identifier
     * @param callback : callback when the details are retrived
     */
    fun getMovieDetail(id: Int, callback: Callback<MovieDetailDTO>)
}