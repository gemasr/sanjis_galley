package com.example.sanjisgalley.ui.recipes

import com.example.sanjisgalley.domain.model.RecipeAvailability

data class RecipeDetailUiState(
  val isLoading: Boolean = true,
  val availability: RecipeAvailability? = null,
  /** One-time notice: the UI shows it and acknowledges with [RecipeDetailUiEvent.NoticeShown]. */
  val showAddedNotice: Boolean = false,
)

sealed interface RecipeDetailUiEvent {
  data object BackClicked : RecipeDetailUiEvent

  data object AddMissingClicked : RecipeDetailUiEvent

  data object NoticeShown : RecipeDetailUiEvent
}
