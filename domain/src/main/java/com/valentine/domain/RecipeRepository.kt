package com.valentine.domain

import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    /**
     * Searches for recipes containing the given ingredient.
     * Returns a Flow that emits a list of summarized recipe info.
     */
    suspend fun searchRecipesByIngredient(ingredients: List<String>): List<RecipeSummary>

    /**
     * Gets the full details for a specific recipe by its ID.
     */
    suspend fun getRecipeDetails(id: String): Recipe

    /**
     * Gets a single random recipe.
     */
    suspend fun getRandomRecipe(): Recipe

    /**
     * Saves a recipe to the local database as a favorite.
     */
    suspend fun saveFavorite(recipe: Recipe)

    /**
     * Removes a recipe from the favorites in the local database.
     */
    suspend fun removeFavorite(recipeId: String)

    /**
     * Retrieves all favorite recipes from the local database.
     * Returns a Flow, so the UI can automatically update when favorites change.
     */
    fun getFavoriteRecipes(): Flow<List<Recipe>>

    /**
     * Checks if a specific recipe is a favorite.
     * Returns a Flow to observe its favorite status in real-time.
     */
    fun isFavorite(recipeId: String): Flow<Boolean>

}