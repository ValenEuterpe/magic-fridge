package com.valentine.feature_search.presentation

import com.valentine.domain.Recipe

data class RecipeDetailState(
    val isLoading: Boolean = false,
    val recipe: Recipe? = null,
    val error: String? = null,
    val isFavorite: Boolean = false
)