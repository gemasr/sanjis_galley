package com.example.sanjisgalley.domain.usecase

import com.example.sanjisgalley.domain.model.RecipeAvailability
import com.example.sanjisgalley.domain.repository.PantryRepository
import com.example.sanjisgalley.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetRecipesAvailabilityUseCase(
  private val recipeRepository: RecipeRepository,
  private val pantryRepository: PantryRepository,
) {
  operator fun invoke(): Flow<List<RecipeAvailability>> =
    combine(recipeRepository.recipes, pantryRepository.items) { recipes, pantry ->
      recipes.map { it.availabilityWith(pantry) }
    }
}
