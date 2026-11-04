package com.example.sanjisgalley.domain.usecase

import com.example.sanjisgalley.domain.model.PantryItem
import com.example.sanjisgalley.domain.repository.PantryRepository
import kotlinx.coroutines.flow.Flow

class ObservePantryItemsUseCase(private val pantryRepository: PantryRepository) {
  operator fun invoke(): Flow<List<PantryItem>> = pantryRepository.items
}
