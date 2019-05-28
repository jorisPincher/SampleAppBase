package fr.jorisfavier.movietest.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import fr.jorisfavier.movietest.MovieTestApp
import fr.jorisfavier.movietest.R
import fr.jorisfavier.movietest.manager.IMovieManager
import kotlinx.android.synthetic.main.movie_list_fragment.*
import javax.inject.Inject

class MovieListFragment : Fragment() {

    companion object {
        fun newInstance() = MovieListFragment()
    }

    private lateinit var viewModel: MovieListViewModel

    @Inject
    lateinit var movieManager: IMovieManager

    private var movieAdapter: MovieAdapter = MovieAdapter(ArrayList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MovieTestApp.currentInstance?.movieTestComponent?.inject(this)
        viewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)
        viewModel.movieManager = movieManager
        initRecyclerView()
        initErrorHandler()
        viewModel.loadMovies()
    }

    private fun initRecyclerView() {
        movie_list.layoutManager = GridLayoutManager(context,3)
        movie_list.adapter = movieAdapter
        viewModel.getMovies().observe(this, Observer { movies ->
            if (movies.size > 0) {
                list_loading.visibility = View.GONE
                movieAdapter.updateMovieList(movies)
            }
        })
    }

    private fun initErrorHandler(){
        viewModel.getError().observe(this, Observer { errorMessage ->
            if (errorMessage != null) {
                list_error.text = errorMessage
                list_error.visibility = View.VISIBLE
            }
        })
    }

}
