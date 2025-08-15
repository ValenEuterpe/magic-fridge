package com.valentine.feature_search.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.valentine.domain.Recipe
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSearch: (String) -> Unit,
    onFavoritesClicked: () -> Unit,
    onRecipeClicked: (String) -> Unit
) {
    // Get an instance of the SearchViewModel using Koin
    val viewModel: SearchViewModel = koinViewModel()
    // Observe the uiState as a Compose State. This will cause the UI to recompose
    // whenever the state changes.
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Magic Fridge") },
                actions = {
                    IconButton(onClick = onFavoritesClicked) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorites"
                        )
                    }

                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Search Bar ---
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { query -> viewModel.onEvent(HomeEvent.OnSearchQueryChanged(query)) },
                placeholder = {Text("e.g., chicken, garlic, ...")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    if (uiState.searchQuery.isNotBlank()) {
                        onSearch(uiState.searchQuery)
                    }
                    focusManager.clearFocus()
                })
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                if (uiState.searchQuery.isNotBlank()) {
                    onSearch(uiState.searchQuery)
                }
                focusManager.clearFocus()
            }) {
                Text("Search Recipes")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Random Recipe Section ---
            Text("Don't know what to cook?", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.onEvent(HomeEvent.OnRandomRecipeClicked) }) {
                Text("Get a Random Recipe")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Content Display ---
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val currentError = uiState.error
                val currentRecipe = uiState.randomRecipe

                when {
                    uiState.isRandomRecipeLoading -> {
                        CircularProgressIndicator()
                    }

                    currentError != null -> {
                        Text(
                            text = currentError,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }

                    currentRecipe != null -> {
                        // We will create this composable next
                        RandomRecipeCard(recipe = currentRecipe!!,
                            onClick = { onRecipeClicked(uiState.randomRecipe!!.id)})
                    }
                }
            }
        }
    }
}

@Composable
fun RandomRecipeCard(
    recipe: Recipe,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = recipe.thumbnailUrl,
                contentDescription = recipe.name,
                modifier = Modifier.fillMaxWidth(0.5f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = recipe.category, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
