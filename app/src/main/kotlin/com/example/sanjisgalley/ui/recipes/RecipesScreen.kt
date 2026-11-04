package com.example.sanjisgalley.ui.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sanjisgalley.SanjisGalleyApplication
import com.example.sanjisgalley.domain.model.RecipeAvailability
import com.example.sanjisgalley.ui.common.AvailabilityLabel

/** Connects the ViewModel with the stateless [RecipesScreen]; opening a recipe is a navigation action. */
@Composable
fun RecipesRoute(onRecipeClick: (String) -> Unit, modifier: Modifier = Modifier) {
  val application = LocalContext.current.applicationContext as SanjisGalleyApplication
  val viewModel: RecipesViewModel = viewModel(factory = RecipesViewModel.Factory(application.container))
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  RecipesScreen(
    uiState = uiState,
    onEvent = { event ->
      when (event) {
        is RecipesUiEvent.RecipeClicked -> onRecipeClick(event.recipeId)
      }
    },
    modifier = modifier,
  )
}

@Composable
fun RecipesScreen(uiState: RecipesUiState, onEvent: (RecipesUiEvent) -> Unit, modifier: Modifier = Modifier) {
  LazyColumn(
    modifier = modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    items(uiState.recipes, key = { it.recipe.id }) { availability ->
      RecipeCard(availability, onClick = { onEvent(RecipesUiEvent.RecipeClicked(availability.recipe.id)) })
    }
  }
}

@Composable
private fun RecipeCard(availability: RecipeAvailability, onClick: () -> Unit) {
  Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(availability.recipe.name, style = MaterialTheme.typography.titleMedium)
      Spacer(Modifier.height(4.dp))
      Text(availability.recipe.description, style = MaterialTheme.typography.bodyMedium)
      Spacer(Modifier.height(12.dp))
      AvailabilityLabel(availability)
    }
  }
}
