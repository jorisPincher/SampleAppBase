package fr.jorisfavier.movietest.ui.movielist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.jorisfavier.movietest.R
import fr.jorisfavier.movietest.models.Movie
import fr.jorisfavier.movietest.utils.inflate

class MovieAdapter(private var movieList: List<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = parent.inflate(R.layout.movie_viewholder, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    fun updateMovieList(movies: List<Movie>){
        movieList = movies
        notifyDataSetChanged()
    }

}