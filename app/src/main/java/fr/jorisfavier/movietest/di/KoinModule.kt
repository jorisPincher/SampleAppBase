package fr.jorisfavier.movietest.di

import fr.jorisfavier.movietest.api.MovieDbService
import fr.jorisfavier.movietest.repository.MovieRepository
import fr.jorisfavier.movietest.repository.impl.MovieRepositoryImpl
import fr.jorisfavier.movietest.ui.moviedetail.MovieDetailViewModel
import fr.jorisfavier.movietest.ui.movielist.MovieListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single {
        MovieDbService.create()
    }

    single<MovieRepository> {
        MovieRepositoryImpl(movieDbService = get())
    }

    viewModel {
        MovieListViewModel(movieRepository = get())
    }

    viewModel { (movieId: Int) ->
        MovieDetailViewModel(movieId = movieId, movieRepository = get())
    }
}