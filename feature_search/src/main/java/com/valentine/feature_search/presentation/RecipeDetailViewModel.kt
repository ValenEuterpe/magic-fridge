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

sealed interface RecipeDetailEvent{
    object OnToggleFavorite: RecipeDetailEvent
}

class RecipeDetailViewModel(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeDetailState())
    val uiState: StateFlow<RecipeDetailState> = _uiState.asStateFlow()

    private val recipeId: String = savedStateHandle.get<String>("recipeId")!!

    init {
        // Observe the favorite status in real-time
        viewModelScope.launch {
            recipeRepository.isFavorite(recipeId).collect { isFavorite ->
                _uiState.update { it.copy(isFavorite = isFavorite) }
            }
        }

        getRecipeDetails()
    }

    fun onEvent(event: RecipeDetailEvent) {
        when (event) {
            RecipeDetailEvent.OnToggleFavorite -> toggleFavorite()
        }
    }

    private fun toggleFavorite() {
        viewModelScope.launch {
            val currentRecipe = _uiState.value.recipe ?: return@launch
            if (_uiState.value.isFavorite) {
                recipeRepository.removeFavorite(currentRecipe.id)
            } else {
                recipeRepository.saveFavorite(currentRecipe)
            }
        }
    }

    private fun getRecipeDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val recipe = recipeRepository.getRecipeDetails(recipeId)
                _uiState.update { it.copy(isLoading = false, recipe = recipe) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Failed to load recipe details.") }
            }
        }
    }

}