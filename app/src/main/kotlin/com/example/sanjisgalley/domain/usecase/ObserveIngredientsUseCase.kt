package com.example.sanjisgalley.domain.usecase

import com.example.sanjisgalley.domain.model.Ingredient
import com.example.sanjisgalley.domain.repository.IngredientRepository
import kotlinx.coroutines.flow.Flow

class ObserveIngredientsUseCase(private val ingredientRepository: IngredientRepository) {
  operator fun invoke(): Flow<List<Ingredient>> = ingredientRepository.ingredients
}
