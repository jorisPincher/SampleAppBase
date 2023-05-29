package fr.jorisfavier.movietest.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import fr.jorisfavier.movietest.databinding.MovieViewholderBinding
import fr.jorisfavier.movietest.models.Movie

class MovieAdapter(
    private val onItemClicked: (Int) -> Unit,
) : ListAdapter<Movie, MovieViewHolder>(Movie.diffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(
            onItemClicked = onItemClicked,
            binding = MovieViewholderBinding.inflate(inflater, parent, false),
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


}