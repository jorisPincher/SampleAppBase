package fr.jorisfavier.movietest.models

import androidx.recyclerview.widget.DiffUtil
import fr.jorisfavier.movietest.models.dto.MovieDetailDTO
import java.util.*

data class Movie(
    val id: Int,
    val title: String,
    val language: String,
    val overview: String,
    val releaseDate: Date,
    val genre: String,
    val posterPath: String
) {
    companion object {
        fun from(movieDetailDTO: MovieDetailDTO): Movie {
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

        val diffUtilCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}