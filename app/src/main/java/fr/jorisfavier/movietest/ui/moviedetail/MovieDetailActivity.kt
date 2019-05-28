package fr.jorisfavier.movietest.ui.moviedetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import fr.jorisfavier.movietest.MovieTestApp
import fr.jorisfavier.movietest.R
import fr.jorisfavier.movietest.api.MovieDbService
import fr.jorisfavier.movietest.manager.IMovieManager
import fr.jorisfavier.movietest.models.Movie
import kotlinx.android.synthetic.main.movie_detail_activity.*
import kotlinx.android.synthetic.main.movie_detail_content.*
import java.text.DateFormat
import javax.inject.Inject

class MovieDetailActivity: AppCompatActivity() {

    companion object {
        val movieIdKey = "movieIdKey"
    }

    private lateinit var viewModel: MovieDetailViewModel
    @Inject
    lateinit var movieManager: IMovieManager
    private var movieId: Int? = null



    private fun initObserver(){
        viewModel.getMovie().observe(this, Observer { movie ->
            buildView(movie)
        })
    }

    private fun buildView(movie: Movie){
        detail_genre.text = movie.genre
        Glide.with(this)
            .load(MovieDbService.imageBaseUrl+movie.posterPath)
            .placeholder(ColorDrawable(Color.GRAY))
            .centerCrop()
            .into(detail_image)
        detail_language.text = movie.language
        detail_overview.text = movie.overview

        detail_release_date.text = DateFormat
            .getDateInstance(DateFormat.SHORT)
            .format(movie.releaseDate)

        detail_title.text = movie.title
    }

    private fun displayError(){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail_activity)
        movieId = intent.getIntExtra(movieIdKey, 0)
        MovieTestApp.currentInstance?.movieTestComponent?.inject(this)
        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        viewModel.movieManager = movieManager
        initObserver()
        if(movieId != null && movieId!! > 0) {
            viewModel.loadDetail(movieId!!)
        }
        else {
            displayError()
        }
    }
}