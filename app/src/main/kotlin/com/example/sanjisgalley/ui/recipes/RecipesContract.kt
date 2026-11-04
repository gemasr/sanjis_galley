package com.example.sanjisgalley.ui.recipes

import com.example.sanjisgalley.domain.model.RecipeAvailability

data class RecipesUiState(
  val isLoading: Boolean = true,
  val recipes: List<RecipeAvailability> = emptyList(),
)

sealed interface RecipesUiEvent {
  data class RecipeClicked(val recipeId: String) : RecipesUiEvent
}
