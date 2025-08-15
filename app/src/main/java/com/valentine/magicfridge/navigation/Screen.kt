package com.valentine.magicfridge.navigation

sealed class Screen(val route: String) {

    object Home: Screen("home")

    object SearchResult : Screen("search_results/{query}"){
        fun createRoute(query: String) = "search_results/$query"
    }

    object RecipeDetail : Screen("recipe_detail/{recipeId}") {
        fun createRoute(recipeId: String) = "recipe_detail/$recipeId"
    }

    object Favorites : Screen("favorites")


}