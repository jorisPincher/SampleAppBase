package fr.jorisfavier.movietest.ui.movielist

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import fr.jorisfavier.movietest.R
import fr.jorisfavier.movietest.databinding.MovieListFragmentBinding
import fr.jorisfavier.movietest.ui.moviedetail.MovieDetailFragment
import fr.jorisfavier.movietest.utils.autoCleared
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment(R.layout.movie_list_fragment) {

    private val binding by autoCleared {
        MovieListFragmentBinding.bind(requireView())
    }

    private val viewModel: MovieListViewModel by viewModel()

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(
            onItemClicked = this::navigateToMovieDetail,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initErrorHandler()
        viewModel.loadMovies()
    }

    private fun initRecyclerView() {
        with(binding) {
            movieList.layoutManager = GridLayoutManager(context, 3)
            movieList.adapter = movieAdapter
            viewModel.movies.observe(viewLifecycleOwner) { movies ->
                if (movies.isNotEmpty()) {
                    listLoading.visibility = View.GONE
                    movieAdapter.submitList(movies)
                }
            }
        }
    }

    private fun initErrorHandler() {
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                binding.listError.text = errorMessage
                binding.listError.visibility = View.VISIBLE
            }
        }
    }

    private fun navigateToMovieDetail(id: Int) {
        findNavController().navigate(
            R.id.movieDetailFragment,
            bundleOf(MovieDetailFragment.movieIdKey to id)
        )
    }

}
