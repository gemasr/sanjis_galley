package com.example.sanjisgalley.data.di

import com.example.sanjisgalley.data.SampleData
import com.example.sanjisgalley.data.repository.InMemoryIngredientRepository
import com.example.sanjisgalley.data.repository.InMemoryPantryRepository
import com.example.sanjisgalley.data.repository.InMemoryRecipeRepository
import com.example.sanjisgalley.data.repository.InMemoryShoppingRepository
import com.example.sanjisgalley.domain.repository.IngredientRepository
import com.example.sanjisgalley.domain.repository.PantryRepository
import com.example.sanjisgalley.domain.repository.RecipeRepository
import com.example.sanjisgalley.domain.repository.ShoppingRepository
import com.example.sanjisgalley.domain.usecase.AddMissingToShoppingListUseCase
import com.example.sanjisgalley.domain.usecase.AddPantryItemUseCase
import com.example.sanjisgalley.domain.usecase.GetRecipeAvailabilityUseCase
import com.example.sanjisgalley.domain.usecase.GetRecipesAvailabilityUseCase
import com.example.sanjisgalley.domain.usecase.ObserveIngredientsUseCase
import com.example.sanjisgalley.domain.usecase.ObservePantryItemsUseCase
import com.example.sanjisgalley.domain.usecase.UpdatePantryItemQuantityUseCase

/** Manual dependency container: creates the repositories once, seeded with the sample data. */
class AppContainer {
  val ingredientRepository: IngredientRepository = InMemoryIngredientRepository(SampleData.ingredients)
  val pantryRepository: PantryRepository = InMemoryPantryRepository(SampleData.pantry)
  val recipeRepository: RecipeRepository = InMemoryRecipeRepository(SampleData.recipes)
  val shoppingRepository: ShoppingRepository = InMemoryShoppingRepository()

  val getRecipesAvailability = GetRecipesAvailabilityUseCase(recipeRepository, pantryRepository)
  val getRecipeAvailability = GetRecipeAvailabilityUseCase(recipeRepository, pantryRepository)
  val addPantryItem = AddPantryItemUseCase(pantryRepository)
  val updatePantryItemQuantity = UpdatePantryItemQuantityUseCase(pantryRepository)
  val observePantryItems = ObservePantryItemsUseCase(pantryRepository)
  val observeIngredients = ObserveIngredientsUseCase(ingredientRepository)
  val addMissingToShoppingList =
    AddMissingToShoppingListUseCase(recipeRepository, pantryRepository, shoppingRepository)
}
