package com.valentine.feature_search.presentation

import com.valentine.domain.Recipe

data class HomeState(
    val searchQuery: String = "",
    val isRandomRecipeLoading: Boolean = false,
    val randomRecipe: Recipe? = null,
    val error: String? = null
)