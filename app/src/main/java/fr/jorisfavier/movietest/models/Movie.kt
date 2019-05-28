package fr.jorisfavier.movietest.models

import fr.jorisfavier.movietest.models.dto.MovieDetailDTO
import java.util.*

class Movie(val id: Int,
            val title: String,
            val language: String,
            val overview: String,
            val releaseDate: Date,
            val genre: String,
            val posterPath: String){
    companion object {
        fun from(movieDetailDTO: MovieDetailDTO): Movie{
            return Movie(movieDetailDTO.id,
                movieDetailDTO.title,
                movieDetailDTO.language,
                movieDetailDTO.overview,
                movieDetailDTO.releaseDate,
                movieDetailDTO.genres
                    .map { it.name }
                    .joinToString(separator = " - "),
                movieDetailDTO.posterPath)
        }
    }
}