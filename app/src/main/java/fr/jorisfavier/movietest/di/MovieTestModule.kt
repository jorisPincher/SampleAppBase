package fr.jorisfavier.movietest.di

import android.app.Application
import dagger.Module
import dagger.Provides
import fr.jorisfavier.movietest.api.MovieDbService
import fr.jorisfavier.movietest.manager.IMovieManager
import fr.jorisfavier.movietest.manager.impl.MovieManager
import javax.inject.Singleton

@Module
class MovieTestModule(val application: Application) {

    @Singleton
    @Provides
    fun applicationProvider(): Application {
        return application
    }

    @Singleton
    @Provides
    fun movieDbServiceProvider(): MovieDbService {
        return MovieDbService.create()
    }

    @Singleton
    @Provides
    fun movieManagerProvider(movieDbService: MovieDbService): IMovieManager {
        return MovieManager(movieDbService)
    }
}