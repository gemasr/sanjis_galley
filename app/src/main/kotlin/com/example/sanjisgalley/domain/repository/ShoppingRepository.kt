package com.example.sanjisgalley.domain.repository

import com.example.sanjisgalley.domain.model.Ingredient
import com.example.sanjisgalley.domain.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
  val items: Flow<List<ShoppingItem>>

  /**
   * Adds [quantity] of [ingredient] to buy. If the ingredient is already in the list and not
   * purchased, the quantity is summed to that line instead of creating a duplicate.
   */
  fun add(ingredient: Ingredient, quantity: Int)
}
