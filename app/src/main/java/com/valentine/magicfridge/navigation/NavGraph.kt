package com.valentine.magicfridge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.valentine.feature_favorites.presentation.FavoritesScreen
import com.valentine.feature_search.presentation.HomeScreen
import com.valentine.feature_search.presentation.RecipeDetailScreen
import com.valentine.feature_search.presentation.SearchResultsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onSearch = { query ->
                    navController.navigate(
                        Screen.SearchResult.createRoute(query)
                    )
                },
                onFavoritesClicked = {
                    navController.navigate(Screen.Favorites.route)
                },
                onRecipeClicked = { recipeId ->
                    navController.navigate(
                        Screen.RecipeDetail.createRoute(recipeId)
                    )
                }
            )
        }

        composable(
            route = Screen.SearchResult.route,
            arguments = listOf(navArgument("query") {type = NavType.StringType})
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query")?: ""
            SearchResultsScreen(query = query,
                onRecipeClick = {recipeId->
                    navController.navigate(
                        Screen.RecipeDetail.createRoute(recipeId)
                    )
                }
            )
        }


        composable(
            route = Screen.RecipeDetail.route,
            arguments = listOf(navArgument("recipeId") {type = NavType.StringType})
        ) {
            RecipeDetailScreen(
                onBackClick = {navController.popBackStack()}
            )
        }

        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                onRecipeClick = {recipeId->
                    navController.navigate(
                        Screen.RecipeDetail.createRoute(recipeId)
                    )
                },
                onBackClick = {navController.popBackStack()}
            )
        }


    }
}