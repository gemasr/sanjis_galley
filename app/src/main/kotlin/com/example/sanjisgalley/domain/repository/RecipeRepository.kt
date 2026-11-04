package com.example.sanjisgalley.domain.repository

import com.example.sanjisgalley.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
  val recipes: Flow<List<Recipe>>

  fun getRecipe(id: String): Recipe?
}
