package com.valentine.magicfridge

import android.app.Application
import com.valentine.data.dataModule
import com.valentine.feature_favorites.di.favoritesModule
import com.valentine.feature_search.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MagicFridgeApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MagicFridgeApplication)
            modules(
                dataModule,
                searchModule,
                favoritesModule
            )
        }
    }
}