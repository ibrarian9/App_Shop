package com.app.capstone

import android.app.Application
import com.app.capstone.core.di.databaseModule
import com.app.capstone.core.di.networkModule
import com.app.capstone.core.di.repositoryModule
import com.app.capstone.di.useCaseModule
import com.app.capstone.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}