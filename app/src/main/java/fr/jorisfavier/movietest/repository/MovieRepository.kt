package fr.jorisfavier.movietest.repository

import fr.jorisfavier.movietest.models.Movie

interface MovieRepository {
    /**
     * Retrieve the last changed movies from MovieDB
     * @param since : number of days since the last change (must be between 0 and 14)
     */
    suspend fun getLastChangedMovies(since: Int): List<Movie>

    /**
     * Get the movie's detailed informations
     * @param id : the movie identifier
     */
    suspend fun getMovieDetail(id: Int): Movie
}