package com.example.sanjisgalley.domain.repository

import com.example.sanjisgalley.domain.model.Ingredient
import com.example.sanjisgalley.domain.model.PantryItem
import kotlinx.coroutines.flow.Flow

interface PantryRepository {
  val items: Flow<List<PantryItem>>

  /** Adds [quantity] of [ingredient]; if it is already in the pantry, the quantity is summed. */
  fun add(ingredient: Ingredient, quantity: Int)
}
