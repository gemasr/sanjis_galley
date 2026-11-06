package com.example.sanjisgalley.ui.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.sanjisgalley.data.di.AppContainer
import com.example.sanjisgalley.domain.usecase.ObserveShoppingItemsUseCase
import com.example.sanjisgalley.domain.usecase.SetShoppingItemPurchasedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShoppingViewModel(
  observeShoppingItems: ObserveShoppingItemsUseCase,
  private val setShoppingItemPurchased: SetShoppingItemPurchasedUseCase,
) : ViewModel() {
  private val _uiState = MutableStateFlow(ShoppingUiState())
  val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

  init {
    viewModelScope.launch {
      observeShoppingItems().collect { items -> _uiState.update { it.copy(isLoading = false, items = items) } }
    }
  }

  fun onEvent(event: ShoppingUiEvent) {
    when (event) {
      is ShoppingUiEvent.PurchasedChanged ->
        viewModelScope.launch { setShoppingItemPurchased(event.ingredientId, event.purchased) }
    }
  }

  class Factory(private val container: AppContainer) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
      ShoppingViewModel(container.observeShoppingItems, container.setShoppingItemPurchased) as T
  }
}
