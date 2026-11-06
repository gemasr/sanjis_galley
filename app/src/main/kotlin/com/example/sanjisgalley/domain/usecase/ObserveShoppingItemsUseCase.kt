package com.example.sanjisgalley.domain.usecase

import com.example.sanjisgalley.domain.model.ShoppingItem
import com.example.sanjisgalley.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveShoppingItemsUseCase(private val shoppingRepository: ShoppingRepository) {
  /** The shopping list with the purchased items at the end (the order is otherwise kept). */
  operator fun invoke(): Flow<List<ShoppingItem>> =
    shoppingRepository.items.map { items -> items.sortedBy { it.purchased } }
}
