package fr.jorisfavier.movietest.models.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class MovieDetailDTO(
    val id: Int,
    val title: String,
    @SerializedName("original_language")
    val language: String,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: Date,
    val genres: List<GenreDTO>,
    @SerializedName("poster_path")
    val posterPath: String,
)