package fr.jorisfavier.movietest.di

import dagger.Component
import fr.jorisfavier.movietest.ui.moviedetail.MovieDetailActivity
import fr.jorisfavier.movietest.ui.movielist.MovieListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [MovieTestModule::class])
interface MovieTestComponent {
    fun inject(movieListFrgmt: MovieListFragment)
    fun inject(movieDetailActivity: MovieDetailActivity)
}