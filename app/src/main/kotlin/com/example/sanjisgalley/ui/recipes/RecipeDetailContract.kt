package com.example.sanjisgalley.ui.recipes

import com.example.sanjisgalley.domain.model.RecipeAvailability

data class RecipeDetailUiState(
  val isLoading: Boolean = true,
  val availability: RecipeAvailability? = null,
)

sealed interface RecipeDetailUiEvent {
  data object BackClicked : RecipeDetailUiEvent
}
