package com.valentine.data.repository

import com.valentine.data.local.RecipeDao
import com.valentine.data.mapper.toDomain
import com.valentine.data.mapper.toEntity
import com.valentine.data.mapper.toRecipe
import com.valentine.data.mapper.toRecipeSummary
import com.valentine.data.remote.MealListDto
import com.valentine.domain.Recipe
import com.valentine.domain.RecipeRepository
import com.valentine.domain.RecipeSummary
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class RecipeRepositoryImpl(
    private val httpClient: HttpClient,
    private val recipeDao: RecipeDao
) : RecipeRepository {

    private val baseUrl = "https://www.themealdb.com/api/json/v1/1"

    override suspend fun searchRecipesByIngredient(ingredients: List<String>): List<RecipeSummary> {
        // Edge case: If the user searches for nothing, return an empty list.
        if (ingredients.isEmpty()) {
            return emptyList()
        }

        return try {
            // This will run all our network calls in parallel for maximum speed.
            coroutineScope {
                val searchResultsLists = ingredients.map { ingredient ->
                    // 'async' starts a new coroutine that runs in parallel.
                    async {
                        val response: MealListDto = httpClient.get("$baseUrl/filter.php?i=$ingredient").body()
                        response.meals?.map { it.toRecipeSummary() } ?: emptyList()
                    }
                }.awaitAll() // '.awaitAll()' waits for all the parallel calls to complete.

                // If any of the searches returned no results, the intersection will be empty.
                if (searchResultsLists.any { it.isEmpty() }) {
                    return@coroutineScope emptyList()
                }

                val initialSet = searchResultsLists.first().toSet()
                val commonRecipes = searchResultsLists.drop(1).fold(initialSet) { common, nextList ->
                    common.intersect(nextList.toSet())
                }

                commonRecipes.toList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getRecipeDetails(id: String): Recipe {
        return try {
            val response: MealListDto = httpClient.get("$baseUrl/lookup.php?i=$id").body()
            // The lookup API returns a list with one meal. We take the first one,
            // map it to our domain Recipe, and throw an exception if it's null.
            response.meals?.firstOrNull()?.toRecipe()
                ?: throw NoSuchElementException("Recipe with ID $id not found.")
        } catch (e: Exception) {
            e.printStackTrace()
            // Re-throw the exception to let the caller (e.g., a ViewModel) know something went wrong.
            throw e
        }
    }

    override suspend fun getRandomRecipe(): Recipe {
        return try {
            val response: MealListDto = httpClient.get("$baseUrl/random.php").body()
            // The random API is similar to lookup, it returns a list with one meal.
            response.meals?.firstOrNull()?.toRecipe()
                ?: throw IllegalStateException("The API did not return a random recipe.")
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun saveFavorite(recipe: Recipe) {
        recipeDao.saveFavorite(recipe.toEntity())
    }

    override suspend fun removeFavorite(recipeId: String) {
        recipeDao.removeFavorite(recipeId)
    }

    override fun getFavoriteRecipes(): Flow<List<Recipe>> {
        return recipeDao.getFavoriteRecipes().map { entityList ->
            entityList.map { it.toDomain() }
        }
    }

    override fun isFavorite(recipeId: String): Flow<Boolean> {
        return recipeDao.isFavorite(recipeId)
    }
}