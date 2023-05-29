package fr.jorisfavier.movietest.ui.moviedetail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import fr.jorisfavier.movietest.R
import fr.jorisfavier.movietest.api.MovieDbService
import fr.jorisfavier.movietest.databinding.MovieDetailFragmentBinding
import fr.jorisfavier.movietest.models.Movie
import fr.jorisfavier.movietest.utils.autoCleared
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.DateFormat

class MovieDetailFragment : Fragment(R.layout.movie_detail_fragment) {

    private val binding by autoCleared {
        MovieDetailFragmentBinding.bind(requireView())
    }

    private val viewModel: MovieDetailViewModel by viewModel {
        parametersOf(requireArguments().getInt(movieIdKey))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            buildView(movie)
        }
    }

    private fun buildView(movie: Movie) {
        with(binding) {
            content?.detailGenre?.text = movie.genre
            Glide.with(this@MovieDetailFragment)
                .load(MovieDbService.imageBaseUrl + movie.posterPath)
                .placeholder(ColorDrawable(Color.GRAY))
                .centerCrop()
                .into(detailImage)

            content?.detailLanguage?.text = movie.language
            content?.detailOverview?.text = movie.overview

            content?.detailReleaseDate?.text = DateFormat
                .getDateInstance(DateFormat.SHORT)
                .format(movie.releaseDate)

            content?.detailTitle?.text = movie.title
        }
    }

    companion object {
        const val movieIdKey = "movieId"
    }
}