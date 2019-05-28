package fr.jorisfavier.movietest

import android.app.Application
import fr.jorisfavier.movietest.di.DaggerMovieTestComponent
import fr.jorisfavier.movietest.di.MovieTestComponent
import fr.jorisfavier.movietest.di.MovieTestModule

class MovieTestApp : Application() {
    companion object {
        var currentInstance: MovieTestApp? = null
    }

    var movieTestComponent: MovieTestComponent? = null
    private set

    override fun onCreate() {
        super.onCreate()
        currentInstance = this
        movieTestComponent = DaggerMovieTestComponent
            .builder()
            .movieTestModule(MovieTestModule(this))
            .build()
    }
}