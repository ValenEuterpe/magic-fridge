package com.valentine.data.mapper

import com.valentine.data.local.RecipeEntity
import com.valentine.data.remote.MealDto
import com.valentine.domain.Ingredient
import com.valentine.domain.Recipe
import com.valentine.domain.RecipeSummary

fun MealDto.toRecipeSummary(): RecipeSummary {
    return RecipeSummary(
        id = this.idMeal,
        name = this.strMeal,
        thumbnailUrl = this.strMealThumb ?: ""
    )
}

fun MealDto.toRecipe(): Recipe {
    val ingredients = mutableListOf<Ingredient>()
    listOf(
        this.strIngredient1 to this.strMeasure1,
        this.strIngredient2 to this.strMeasure2,
        this.strIngredient3 to this.strMeasure3,
        this.strIngredient4 to this.strMeasure4,
        this.strIngredient5 to this.strMeasure5,
        this.strIngredient6 to this.strMeasure6,
        this.strIngredient7 to this.strMeasure7,
        this.strIngredient8 to this.strMeasure8,
        this.strIngredient9 to this.strMeasure9,
        this.strIngredient10 to this.strMeasure10,
        this.strIngredient11 to this.strMeasure11,
        this.strIngredient12 to this.strMeasure12,
        this.strIngredient13 to this.strMeasure13,
        this.strIngredient14 to this.strMeasure14,
        this.strIngredient15 to this.strMeasure15,
        this.strIngredient16 to this.strMeasure16,
        this.strIngredient17 to this.strMeasure17,
        this.strIngredient18 to this.strMeasure18,
        this.strIngredient19 to this.strMeasure19,
        this.strIngredient20 to this.strMeasure20
    ).forEach { (ingredient, measure) ->
        if (!ingredient.isNullOrBlank()) {
            ingredients.add(
                Ingredient(
                    name = ingredient,
                    measure = measure.orEmpty()
                )
            )
        }
    }

    return Recipe(
        id = this.idMeal,
        name = this.strMeal,
        category = this.strCategory ?: "Unknown",
        area = this.strArea ?: "Unknown",
        instructions = this.strInstructions ?: "",
        thumbnailUrl = this.strMealThumb ?: "",
        tags = this.strTags?.split(",")?.map { it.trim() } ?: emptyList(),
        youtubeUrl = this.strYoutube,
        ingredients = ingredients
    )
}

fun Recipe.toEntity(): RecipeEntity {
    return RecipeEntity(
        id = this.id,
        name = this.name,
        category = this.category,
        area = this.area,
        instructions = this.instructions,
        thumbnailUrl = this.thumbnailUrl,
        tags = this.tags,
        youtubeUrl = this.youtubeUrl,
        ingredients = this.ingredients
    )
}

// Maps a database RecipeEntity to a domain Recipe for using in the app.
fun RecipeEntity.toDomain(): Recipe {
    return Recipe(
        id = this.id,
        name = this.name,
        category = this.category,
        area = this.area,
        instructions = this.instructions,
        thumbnailUrl = this.thumbnailUrl,
        tags = this.tags,
        youtubeUrl = this.youtubeUrl,
        ingredients = this.ingredients
    )
}

fun RecipeEntity.toRecipeSummary(): RecipeSummary {
    return RecipeSummary(
        id = this.id,
        name = this.name,
        thumbnailUrl = this.thumbnailUrl
    )
}

fun Recipe.toRecipeSummary(): RecipeSummary {
    return RecipeSummary(
        id = this.id,
        name = this.name,
        thumbnailUrl = this.thumbnailUrl
    )
}

