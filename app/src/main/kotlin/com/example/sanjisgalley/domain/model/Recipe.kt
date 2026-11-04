package com.example.sanjisgalley.domain.model

data class RecipeIngredient(
  val ingredient: Ingredient,
  val quantity: Int,
)

data class Recipe(
  val id: String,
  val name: String,
  val description: String,
  val steps: List<String>,
  val ingredients: List<RecipeIngredient>,
) {
  /** Computes which ingredients are short given the current pantry. */
  fun availabilityWith(pantry: List<PantryItem>): RecipeAvailability {
    val stock = pantry.associate { it.ingredient.id to it.quantity }
    val missing =
      ingredients.mapNotNull { needed ->
        val shortfall = needed.quantity - (stock[needed.ingredient.id] ?: 0)
        if (shortfall > 0) MissingIngredient(needed.ingredient, shortfall) else null
      }
    return RecipeAvailability(recipe = this, missing = missing)
  }
}

data class MissingIngredient(
  val ingredient: Ingredient,
  val quantity: Int,
)

data class RecipeAvailability(
  val recipe: Recipe,
  val missing: List<MissingIngredient>,
) {
  /** A recipe is available when none of its ingredients is short. */
  val isAvailable: Boolean get() = missing.isEmpty()
}
