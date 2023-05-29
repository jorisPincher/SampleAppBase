package fr.jorisfavier.movietest

import android.app.Application
import fr.jorisfavier.movietest.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules)
            androidContext(this@MovieApp)
        }
    }
}