package com.example.sanjisgalley.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.sanjisgalley.data.di.AppContainer
import com.example.sanjisgalley.domain.usecase.GetRecipesAvailabilityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipesViewModel(getRecipesAvailability: GetRecipesAvailabilityUseCase) : ViewModel() {
  private val _uiState = MutableStateFlow(RecipesUiState())
  val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

  init {
    viewModelScope.launch {
      getRecipesAvailability().collect { recipes -> _uiState.update { it.copy(isLoading = false, recipes = recipes) } }
    }
  }

  class Factory(private val container: AppContainer) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
      RecipesViewModel(container.getRecipesAvailability) as T
  }
}
