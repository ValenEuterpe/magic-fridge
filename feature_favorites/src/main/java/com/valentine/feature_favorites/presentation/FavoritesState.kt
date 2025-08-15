package com.valentine.feature_favorites.presentation

import com.valentine.domain.RecipeSummary

data class FavoritesState(
    val recipes: List<RecipeSummary> = emptyList()
)