package com.valentine.data

import android.R.attr.level
import androidx.room.Room
import com.valentine.data.local.AppDatabase
import com.valentine.data.repository.RecipeRepositoryImpl
import com.valentine.domain.RecipeRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module{

    single {
        // This is the HttpClient creation logic we moved from the old KtorClient object
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                })
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }

    // 2. Database Dependencies
    single {
        // This builds the Room database instance.
        Room.databaseBuilder(
            androidContext(), // Provides the application context
            AppDatabase::class.java,
            "magic_fridge.db" // The name of the database file on the device
        ).build()
    }

    single {
        // This gets the RecipeDao from the AppDatabase instance we just defined.
        val database = get<AppDatabase>()
        database.recipeDao()
    }

    // 3. Repository Dependency
    single<RecipeRepository> {
        RecipeRepositoryImpl(
            httpClient = get(),
            recipeDao = get()
        )
    }

}