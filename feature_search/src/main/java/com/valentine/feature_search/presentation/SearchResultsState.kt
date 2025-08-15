package com.valentine.feature_search.presentation

import com.valentine.domain.RecipeSummary

data class SearchResultsState (
    val isLoading: Boolean = false,
    val recipes: List<RecipeSummary> = emptyList(),
    val error: String? = null
)

