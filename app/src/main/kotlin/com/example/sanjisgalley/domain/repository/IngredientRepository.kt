package com.example.sanjisgalley.domain.repository

import com.example.sanjisgalley.domain.model.Ingredient
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {
  /** The known ingredients. */
  val ingredients: Flow<List<Ingredient>>
}
