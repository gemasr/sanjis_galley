package com.example.sanjisgalley.data.repository

import com.example.sanjisgalley.domain.model.Recipe
import com.example.sanjisgalley.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemoryRecipeRepository(initialRecipes: List<Recipe>) : RecipeRepository {
  private val state = MutableStateFlow(initialRecipes)

  override val recipes: Flow<List<Recipe>> = state

  override fun getRecipe(id: String): Recipe? = state.value.firstOrNull { it.id == id }
}
