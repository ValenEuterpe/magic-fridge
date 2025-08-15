package com.valentine.feature_favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentine.domain.RecipeRepository
import com.valentine.data.mapper.toRecipeSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesState())
    val uiState: StateFlow<FavoritesState> = _uiState.asStateFlow()

    init{
        viewModelScope.launch {
            recipeRepository.getFavoriteRecipes()
                .map { recipeList ->
                    recipeList.map { it.toRecipeSummary() }
                }
                .collect { favoriteSummaries ->
                    _uiState.update { it.copy(recipes = favoriteSummaries) }
                }
        }
    }
}