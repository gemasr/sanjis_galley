package com.example.sanjisgalley.ui.shopping

import com.example.sanjisgalley.domain.model.ShoppingItem

data class ShoppingUiState(
  val isLoading: Boolean = true,
  val items: List<ShoppingItem> = emptyList(),
)

sealed interface ShoppingUiEvent {
  data class PurchasedChanged(val ingredientId: String, val purchased: Boolean) : ShoppingUiEvent
}
