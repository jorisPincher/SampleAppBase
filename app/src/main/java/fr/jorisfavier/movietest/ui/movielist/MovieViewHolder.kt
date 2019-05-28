package fr.jorisfavier.movietest.ui.movielist

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.jorisfavier.movietest.api.MovieDbService
import fr.jorisfavier.movietest.models.Movie
import fr.jorisfavier.movietest.ui.moviedetail.MovieDetailActivity
import kotlinx.android.synthetic.main.movie_viewholder.view.*

class MovieViewHolder(movieView: View): RecyclerView.ViewHolder(movieView) {

    private var view = itemView

    fun bind(movie: Movie) {
        Glide.with(view)
            .load(MovieDbService.imageBaseUrl+movie.posterPath)
            .placeholder(ColorDrawable(Color.GRAY))
            .centerCrop()
            .into(view.movie_poster)
        view.movie_name.text = movie.title
        view.setOnClickListener {
            val intent = Intent(view.context,MovieDetailActivity::class.java).apply {
                putExtra(MovieDetailActivity.movieIdKey,movie.id)
            }
            view.context.startActivity(intent)
        }
    }
}