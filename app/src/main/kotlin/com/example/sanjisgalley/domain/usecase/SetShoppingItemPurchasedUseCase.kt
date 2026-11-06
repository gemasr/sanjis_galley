package com.example.sanjisgalley.domain.usecase

import com.example.sanjisgalley.domain.repository.ShoppingRepository

class SetShoppingItemPurchasedUseCase(private val shoppingRepository: ShoppingRepository) {
  operator fun invoke(ingredientId: String, purchased: Boolean) {
    shoppingRepository.setPurchased(ingredientId, purchased)
  }
}
