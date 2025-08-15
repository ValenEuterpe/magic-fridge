package com.valentine.feature_search.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentine.domain.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchResultsViewModel (

    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle

) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchResultsState())
    val uiState : StateFlow<SearchResultsState> = _uiState.asStateFlow()

    private val query: String = savedStateHandle.get<String>("query") ?: ""

    init {
        val ingredients = query.split(',')
            .map { it.trim() }
            .filter { it.isNotBlank() }

        searchRecipes(ingredients)
    }

    private fun searchRecipes(ingredients: List<String>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try{
                val recipes = recipeRepository.searchRecipesByIngredient(ingredients)
                _uiState.update { it.copy(isLoading = false, recipes = recipes) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Failed to load results.") }
            }
        }
    }
}