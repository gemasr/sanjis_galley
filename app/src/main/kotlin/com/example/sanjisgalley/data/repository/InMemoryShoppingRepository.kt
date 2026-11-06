package com.example.sanjisgalley.data.repository

import com.example.sanjisgalley.domain.model.Ingredient
import com.example.sanjisgalley.domain.model.ShoppingItem
import com.example.sanjisgalley.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class InMemoryShoppingRepository(initialItems: List<ShoppingItem> = emptyList()) : ShoppingRepository {
  private val state = MutableStateFlow(initialItems)

  override val items: Flow<List<ShoppingItem>> = state

  override fun setPurchased(ingredientId: String, purchased: Boolean) {
    state.update { current ->
      val source =
        current.firstOrNull { it.ingredient.id == ingredientId && it.purchased != purchased } ?: return@update current
      val target = current.firstOrNull { it.ingredient.id == ingredientId && it.purchased == purchased }
      if (target == null) {
        current.map { if (it === source) it.copy(purchased = purchased) else it }
      } else {
        current.mapNotNull {
          when {
            it === source -> null
            it === target -> it.copy(quantity = it.quantity + source.quantity)
            else -> it
          }
        }
      }
    }
  }

  override fun add(ingredient: Ingredient, quantity: Int) {
    state.update { current ->
      val isOpenLine = { item: ShoppingItem -> item.ingredient.id == ingredient.id && !item.purchased }
      if (current.any(isOpenLine)) {
        current.map { if (isOpenLine(it)) it.copy(quantity = it.quantity + quantity) else it }
      } else {
        current + ShoppingItem(ingredient, quantity)
      }
    }
  }
}
