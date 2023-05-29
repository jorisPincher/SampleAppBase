package fr.jorisfavier.movietest.repository.impl

import android.util.SparseArray
import fr.jorisfavier.movietest.api.MovieDbService
import fr.jorisfavier.movietest.models.Movie
import fr.jorisfavier.movietest.models.dto.MovieDetailDTO
import fr.jorisfavier.movietest.repository.MovieRepository
import java.util.*

class MovieRepositoryImpl(
    private val movieDbService: MovieDbService,
) : MovieRepository {

    //We cached the movies in order to prevent overload
    private var cachedMovies: SparseArray<MovieDetailDTO> = SparseArray()

    override suspend fun getLastChangedMovies(since: Int): List<Movie> {
        val calendar = Calendar.getInstance()
        if (since in 0..14) {
            calendar.add(Calendar.DAY_OF_YEAR, -since)
        }
        val result =
            movieDbService.lastChangedMovies(calendar.time, null, null).results
        return result.filter { !it.adult }.take(20).mapNotNull {
            loadMovieDetails(it.id)
        }
    }

    override suspend fun getMovieDetail(id: Int): Movie {
        //we first check if the detail are not in cache
        val dto = cachedMovies.get(id) ?: movieDbService.getMovieDetail(id, null)
        return Movie.from(dto)
    }

    private suspend fun loadMovieDetails(id: Int): Movie? {
        val dto = kotlin.runCatching {
            movieDbService.getMovieDetail(id, null)
        }.getOrNull()

        //Prevent issue with GsonConverter
        // (https://bytes.babbel.com/en/articles/2018-05-25-kotlin-gson-nullability.html)
        if (dto == null
            || dto.genres == null
            || dto.language == null
            || dto.overview == null
            || dto.id == null
            || dto.posterPath == null
            || dto.releaseDate == null
            || dto.title == null
        ) {
            return null
        }
        return Movie.from(dto)
    }

}