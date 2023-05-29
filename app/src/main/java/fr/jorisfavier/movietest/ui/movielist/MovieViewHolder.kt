package fr.jorisfavier.movietest.ui.movielist

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.jorisfavier.movietest.api.MovieDbService
import fr.jorisfavier.movietest.databinding.MovieViewholderBinding
import fr.jorisfavier.movietest.models.Movie

class MovieViewHolder(
    private val onItemClicked: (Int) -> Unit,
    private val binding: MovieViewholderBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private var movie: Movie? = null

    init {
        binding.root.setOnClickListener {
            val id = movie?.id ?: return@setOnClickListener
            onItemClicked(id)
        }
    }

    fun bind(movie: Movie) {
        this.movie = movie
        with(binding) {
            Glide.with(root)
                .load(MovieDbService.imageBaseUrl + movie.posterPath)
                .placeholder(ColorDrawable(Color.GRAY))
                .centerCrop()
                .into(moviePoster)
            movieName.text = movie.title
        }
    }
}