package com.example.sanjisgalley.data.repository

import com.example.sanjisgalley.domain.model.Ingredient
import com.example.sanjisgalley.domain.model.PantryItem
import com.example.sanjisgalley.domain.repository.PantryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class InMemoryPantryRepository(initialItems: List<PantryItem>) : PantryRepository {
  private val state = MutableStateFlow(initialItems)

  override val items: Flow<List<PantryItem>> = state

  override fun add(ingredient: Ingredient, quantity: Int) {
    state.update { current ->
      if (current.any { it.ingredient.id == ingredient.id }) {
        current.map {
          if (it.ingredient.id == ingredient.id) it.copy(quantity = it.quantity + quantity) else it
        }
      } else {
        current + PantryItem(ingredient, quantity)
      }
    }
  }
}
