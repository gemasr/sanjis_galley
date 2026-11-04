package com.example.sanjisgalley.domain.usecase

import com.example.sanjisgalley.domain.model.Ingredient
import com.example.sanjisgalley.domain.repository.PantryRepository

class UpdatePantryItemQuantityUseCase(private val pantryRepository: PantryRepository) {
  operator fun invoke(ingredient: Ingredient, quantity: Int) {
    require(quantity > 0) { "quantity must be greater than 0" }
    pantryRepository.setQuantity(ingredient, quantity)
  }
}
