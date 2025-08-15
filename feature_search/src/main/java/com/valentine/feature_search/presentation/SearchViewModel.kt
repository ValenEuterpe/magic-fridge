package com.valentine.feature_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentine.domain.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())

    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnSearchQueryChanged -> {
                _uiState.update {
                    it.copy(searchQuery = event.query)
                }
            }

            HomeEvent.OnRandomRecipeClicked -> {
                getRandomRecipe()
            }
        }
    }

    private fun getRandomRecipe() {
        // Launch a coroutine in the viewModelScope.
        // This scope is automatically cancelled when the ViewModel is cleared.
        viewModelScope.launch {
            // Set loading state
            _uiState.update { it.copy(isRandomRecipeLoading = true, error = null) }
            try {
                val recipe = recipeRepository.getRandomRecipe()

                println("DEBUG: Fetched Recipe -> Name: ${recipe.name}, ImageURL: ${recipe.thumbnailUrl}")

                _uiState.update {
                    it.copy(isRandomRecipeLoading = false, randomRecipe = recipe)
                }
            } catch (e: Exception) {
                // Handle error state
                _uiState.update {
                    it.copy(isRandomRecipeLoading = false, error = "Failed to fetch recipe.")
                }
            }
        }
    }


}


sealed interface HomeEvent {
    data class OnSearchQueryChanged(val query: String) : HomeEvent
    object OnRandomRecipeClicked : HomeEvent
}