package com.example.sanjisgalley.ui.pantry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.sanjisgalley.data.di.AppContainer
import com.example.sanjisgalley.domain.usecase.AddPantryItemUseCase
import com.example.sanjisgalley.domain.usecase.ObserveIngredientsUseCase
import com.example.sanjisgalley.domain.usecase.ObservePantryItemsUseCase
import com.example.sanjisgalley.domain.usecase.UpdatePantryItemQuantityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val MAX_QUANTITY_DIGITS = 6

class PantryViewModel(
  observePantryItems: ObservePantryItemsUseCase,
  observeIngredients: ObserveIngredientsUseCase,
  private val addPantryItem: AddPantryItemUseCase,
  private val updatePantryItemQuantity: UpdatePantryItemQuantityUseCase,
) : ViewModel() {
  private val _uiState = MutableStateFlow(PantryUiState())
  val uiState: StateFlow<PantryUiState> = _uiState.asStateFlow()

  init {
    viewModelScope.launch {
      combine(observePantryItems(), observeIngredients()) { items, ingredients -> items to ingredients }
        .collect { (items, ingredients) ->
          _uiState.update { it.copy(isLoading = false, items = items, ingredients = ingredients) }
        }
    }
  }

  fun onEvent(event: PantryUiEvent) {
    when (event) {
      PantryUiEvent.AddClicked ->
        _uiState.update { it.copy(form = PantryForm(isEditing = false, ingredient = null, quantityText = "")) }

      is PantryUiEvent.ItemClicked -> {
        val item = _uiState.value.items.firstOrNull { it.ingredient.id == event.ingredient.id } ?: return
        _uiState.update {
          it.copy(form = PantryForm(isEditing = true, ingredient = item.ingredient, quantityText = item.quantity.toString()))
        }
      }

      is PantryUiEvent.IngredientSelected ->
        _uiState.update { state -> state.copy(form = state.form?.copy(ingredient = event.ingredient)) }

      is PantryUiEvent.QuantityChanged -> {
        val digits = event.text.filter { it.isDigit() }.take(MAX_QUANTITY_DIGITS)
        _uiState.update { state -> state.copy(form = state.form?.copy(quantityText = digits)) }
      }

      PantryUiEvent.SaveClicked -> save()
      PantryUiEvent.FormDismissed -> _uiState.update { it.copy(form = null) }
    }
  }

  private fun save() {
    val form = _uiState.value.form ?: return
    val ingredient = form.ingredient ?: return
    val quantity = form.quantityText.toIntOrNull() ?: return
    if (!form.canSave) return
    viewModelScope.launch {
      if (form.isEditing) updatePantryItemQuantity(ingredient, quantity) else addPantryItem(ingredient, quantity)
    }
    _uiState.update { it.copy(form = null) }
  }

  class Factory(private val container: AppContainer) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
      PantryViewModel(
        container.observePantryItems,
        container.observeIngredients,
        container.addPantryItem,
        container.updatePantryItemQuantity,
      ) as T
  }
}
