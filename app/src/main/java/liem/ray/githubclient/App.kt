package liem.ray.githubclient

import android.app.Application
import liem.ray.githubclient.di.apiModule
import liem.ray.githubclient.di.commonModule
import liem.ray.githubclient.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
    }

    private fun initDependencyInjection() {
        startKoin {
            androidContext(this@App)
            modules(listOf(apiModule, viewModelModule, commonModule))
        }
    }
}