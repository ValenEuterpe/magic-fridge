package com.valentine.feature_favorites.di

import com.valentine.feature_favorites.presentation.FavoritesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel {
        FavoritesViewModel(get())
    }
}