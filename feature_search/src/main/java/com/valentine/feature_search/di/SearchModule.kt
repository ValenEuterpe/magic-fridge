package com.valentine.feature_search.di

import com.valentine.feature_search.presentation.RecipeDetailViewModel
import com.valentine.feature_search.presentation.SearchResultsViewModel
import com.valentine.feature_search.presentation.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchModule = module{


    viewModel {
        SearchViewModel(get())
    }


    viewModel { params->
        SearchResultsViewModel(
            recipeRepository = get(),
            savedStateHandle = params.get()
        )
    }

    viewModel { params ->
        RecipeDetailViewModel(
            recipeRepository = get(),
            savedStateHandle = params.get()
        )
    }
}