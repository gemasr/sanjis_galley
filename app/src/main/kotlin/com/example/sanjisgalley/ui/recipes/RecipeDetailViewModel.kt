package com.example.sanjisgalley.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.sanjisgalley.data.di.AppContainer
import com.example.sanjisgalley.domain.usecase.AddMissingToShoppingListUseCase
import com.example.sanjisgalley.domain.usecase.GetRecipeAvailabilityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
  private val recipeId: String,
  getRecipeAvailability: GetRecipeAvailabilityUseCase,
  private val addMissingToShoppingList: AddMissingToShoppingListUseCase,
) : ViewModel() {
  private val _uiState = MutableStateFlow(RecipeDetailUiState())
  val uiState: StateFlow<RecipeDetailUiState> = _uiState.asStateFlow()

  init {
    viewModelScope.launch {
      getRecipeAvailability(recipeId).collect { availability ->
        _uiState.update { it.copy(isLoading = false, availability = availability) }
      }
    }
  }

  /** [RecipeDetailUiEvent.BackClicked] is navigation and is handled by the route. */
  fun onEvent(event: RecipeDetailUiEvent) {
    when (event) {
      RecipeDetailUiEvent.AddMissingClicked ->
        viewModelScope.launch {
          addMissingToShoppingList(recipeId)
          _uiState.update { it.copy(showAddedNotice = true) }
        }
      RecipeDetailUiEvent.NoticeShown -> _uiState.update { it.copy(showAddedNotice = false) }
      RecipeDetailUiEvent.BackClicked -> Unit
    }
  }

  class Factory(private val container: AppContainer, private val recipeId: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
      RecipeDetailViewModel(recipeId, container.getRecipeAvailability, container.addMissingToShoppingList) as T
  }
}
