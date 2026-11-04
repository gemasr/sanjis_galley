package com.example.sanjisgalley.ui.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sanjisgalley.R
import com.example.sanjisgalley.SanjisGalleyApplication
import com.example.sanjisgalley.domain.model.RecipeAvailability
import com.example.sanjisgalley.domain.model.RecipeIngredient
import com.example.sanjisgalley.ui.common.AvailabilityLabel

/** Connects the ViewModel with the stateless [RecipeDetailScreen]; going back is a navigation action. */
@Composable
fun RecipeDetailRoute(recipeId: String, onBack: () -> Unit, modifier: Modifier = Modifier) {
  val application = LocalContext.current.applicationContext as SanjisGalleyApplication
  val viewModel: RecipeDetailViewModel =
    viewModel(factory = RecipeDetailViewModel.Factory(application.container, recipeId))
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  RecipeDetailScreen(
    uiState = uiState,
    onEvent = { event ->
      when (event) {
        RecipeDetailUiEvent.BackClicked -> onBack()
      }
    },
    modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(uiState: RecipeDetailUiState, onEvent: (RecipeDetailUiEvent) -> Unit, modifier: Modifier = Modifier) {
  Scaffold(
    modifier = modifier,
    contentWindowInsets = WindowInsets(0.dp),
    topBar = {
      TopAppBar(
        title = {},
        navigationIcon = {
          IconButton(onClick = { onEvent(RecipeDetailUiEvent.BackClicked) }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
          }
        },
        windowInsets = WindowInsets(0.dp),
      )
    },
  ) { innerPadding ->
    val availability = uiState.availability
    if (availability != null) {
      RecipeDetailContent(availability, contentPadding = innerPadding)
    }
  }
}

@Composable
private fun RecipeDetailContent(availability: RecipeAvailability, contentPadding: PaddingValues) {
  val recipe = availability.recipe
  val missingById = availability.missing.associateBy { it.ingredient.id }

  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding =
      PaddingValues(
        start = 16.dp,
        end = 16.dp,
        top = contentPadding.calculateTopPadding(),
        bottom = contentPadding.calculateBottomPadding() + 16.dp,
      ),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    item { Text(recipe.name, style = MaterialTheme.typography.headlineSmall) }
    item { Text(recipe.description, style = MaterialTheme.typography.bodyLarge) }
    item { AvailabilityLabel(availability) }

    item { SectionTitle(stringResource(R.string.recipe_ingredients)) }
    items(recipe.ingredients, key = { it.ingredient.id }) { needed ->
      IngredientLine(needed, missing = missingById[needed.ingredient.id]?.quantity)
    }

    item { SectionTitle(stringResource(R.string.recipe_steps)) }
    itemsIndexed(recipe.steps) { index, step ->
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("${index + 1}.", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Text(step, style = MaterialTheme.typography.bodyLarge)
      }
    }
  }
}

@Composable
private fun SectionTitle(title: String) {
  Text(title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
}

@Composable
private fun IngredientLine(needed: RecipeIngredient, missing: Int?) {
  val name = needed.ingredient.name
  val unit = needed.ingredient.unit.symbol
  if (missing == null) {
    Text(stringResource(R.string.quantity_format, name, needed.quantity, unit), style = MaterialTheme.typography.bodyLarge)
  } else {
    Text(
      text = stringResource(R.string.ingredient_missing_format, name, needed.quantity, unit, missing),
      style = MaterialTheme.typography.bodyLarge,
      color = MaterialTheme.colorScheme.error,
      fontWeight = FontWeight.Bold,
    )
  }
}
