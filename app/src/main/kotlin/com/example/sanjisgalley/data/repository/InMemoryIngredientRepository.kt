package com.example.sanjisgalley.data.repository

import com.example.sanjisgalley.domain.model.Ingredient
import com.example.sanjisgalley.domain.repository.IngredientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemoryIngredientRepository(initialIngredients: List<Ingredient>) : IngredientRepository {
  private val state = MutableStateFlow(initialIngredients)

  override val ingredients: Flow<List<Ingredient>> = state
}
