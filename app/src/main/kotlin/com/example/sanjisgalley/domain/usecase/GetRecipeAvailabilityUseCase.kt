package com.example.sanjisgalley.domain.usecase

import com.example.sanjisgalley.domain.model.RecipeAvailability
import com.example.sanjisgalley.domain.repository.PantryRepository
import com.example.sanjisgalley.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRecipeAvailabilityUseCase(
  private val recipeRepository: RecipeRepository,
  private val pantryRepository: PantryRepository,
) {
  /** Emits the availability of the recipe each time the pantry changes; null if the recipe does not exist. */
  operator fun invoke(recipeId: String): Flow<RecipeAvailability?> =
    pantryRepository.items.map { pantry -> recipeRepository.getRecipe(recipeId)?.availabilityWith(pantry) }
}
