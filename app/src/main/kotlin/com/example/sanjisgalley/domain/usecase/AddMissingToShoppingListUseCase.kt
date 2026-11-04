package com.example.sanjisgalley.domain.usecase

import com.example.sanjisgalley.domain.repository.PantryRepository
import com.example.sanjisgalley.domain.repository.RecipeRepository
import com.example.sanjisgalley.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.first

class AddMissingToShoppingListUseCase(
  private val recipeRepository: RecipeRepository,
  private val pantryRepository: PantryRepository,
  private val shoppingRepository: ShoppingRepository,
) {
  /** Adds the missing ingredients of the recipe to the shopping list. Returns how many lines were added. */
  suspend operator fun invoke(recipeId: String): Int {
    val recipe = recipeRepository.getRecipe(recipeId) ?: return 0
    val missing = recipe.availabilityWith(pantryRepository.items.first()).missing
    missing.forEach { shoppingRepository.add(it.ingredient, it.quantity) }
    return missing.size
  }
}
