package com.example.sanjisgalley.ui.pantry

import com.example.sanjisgalley.domain.model.Ingredient
import com.example.sanjisgalley.domain.model.PantryItem

data class PantryUiState(
  val isLoading: Boolean = true,
  val items: List<PantryItem> = emptyList(),
  val ingredients: List<Ingredient> = emptyList(),
  val form: PantryForm? = null,
)

/** The add/edit form. When [isEditing] the ingredient is fixed and only the quantity changes. */
data class PantryForm(
  val isEditing: Boolean,
  val ingredient: Ingredient?,
  val quantityText: String,
) {
  val canSave: Boolean get() = ingredient != null && (quantityText.toIntOrNull() ?: 0) > 0
}

sealed interface PantryUiEvent {
  data object AddClicked : PantryUiEvent

  data class ItemClicked(val ingredient: Ingredient) : PantryUiEvent

  data class IngredientSelected(val ingredient: Ingredient) : PantryUiEvent

  data class QuantityChanged(val text: String) : PantryUiEvent

  data object SaveClicked : PantryUiEvent

  data object FormDismissed : PantryUiEvent
}
