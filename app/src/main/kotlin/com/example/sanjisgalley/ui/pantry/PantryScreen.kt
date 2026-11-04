package com.example.sanjisgalley.ui.pantry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sanjisgalley.R
import com.example.sanjisgalley.SanjisGalleyApplication
import com.example.sanjisgalley.domain.model.Ingredient
import com.example.sanjisgalley.domain.model.PantryItem

/** Connects the ViewModel with the stateless [PantryScreen]. */
@Composable
fun PantryRoute(modifier: Modifier = Modifier) {
  val application = LocalContext.current.applicationContext as SanjisGalleyApplication
  val viewModel: PantryViewModel = viewModel(factory = PantryViewModel.Factory(application.container))
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  PantryScreen(uiState = uiState, onEvent = viewModel::onEvent, modifier = modifier)
}

@Composable
fun PantryScreen(uiState: PantryUiState, onEvent: (PantryUiEvent) -> Unit, modifier: Modifier = Modifier) {
  Scaffold(
    modifier = modifier,
    contentWindowInsets = WindowInsets(0.dp),
    floatingActionButton = {
      ExtendedFloatingActionButton(
        onClick = { onEvent(PantryUiEvent.AddClicked) },
        icon = { Icon(Icons.Default.Add, contentDescription = null) },
        text = { Text(stringResource(R.string.pantry_add_ingredient)) },
      )
    },
  ) { innerPadding ->
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      when {
        uiState.isLoading -> Unit
        uiState.items.isEmpty() ->
          Text(stringResource(R.string.pantry_empty), style = MaterialTheme.typography.bodyLarge)
        else ->
          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.Top,
          ) {
            items(uiState.items, key = { it.ingredient.id }) { item ->
              PantryItemRow(item, onClick = { onEvent(PantryUiEvent.ItemClicked(item.ingredient)) })
              HorizontalDivider()
            }
            // Space so the floating button never hides the last row.
            item { Box(modifier = Modifier.fillMaxWidth().padding(bottom = 88.dp)) }
          }
      }
    }
  }

  uiState.form?.let { form ->
    PantryFormDialog(form = form, ingredients = uiState.ingredients, onEvent = onEvent)
  }
}

@Composable
private fun PantryItemRow(item: PantryItem, onClick: () -> Unit) {
  ListItem(
    headlineContent = {
      Text(
        stringResource(
          R.string.quantity_format,
          item.ingredient.name,
          item.quantity,
          item.ingredient.unit.symbol,
        )
      )
    },
    modifier = Modifier.clickable(onClick = onClick),
  )
}

@Composable
private fun PantryFormDialog(form: PantryForm, ingredients: List<Ingredient>, onEvent: (PantryUiEvent) -> Unit) {
  AlertDialog(
    onDismissRequest = { onEvent(PantryUiEvent.FormDismissed) },
    confirmButton = {
      TextButton(onClick = { onEvent(PantryUiEvent.SaveClicked) }, enabled = form.canSave) {
        Text(stringResource(R.string.save))
      }
    },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        IngredientField(form = form, ingredients = ingredients, onSelected = { onEvent(PantryUiEvent.IngredientSelected(it)) })
        OutlinedTextField(
          value = form.quantityText,
          onValueChange = { onEvent(PantryUiEvent.QuantityChanged(it)) },
          label = { Text(stringResource(R.string.label_quantity)) },
          suffix = form.ingredient?.let { { Text(it.unit.symbol) } },
          singleLine = true,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          modifier = Modifier.fillMaxWidth(),
        )
      }
    },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IngredientField(form: PantryForm, ingredients: List<Ingredient>, onSelected: (Ingredient) -> Unit) {
  var expanded by remember { mutableStateOf(false) }
  val label: @Composable () -> Unit = { Text(stringResource(R.string.label_ingredient)) }

  if (form.isEditing) {
    OutlinedTextField(
      value = form.ingredient?.name.orEmpty(),
      onValueChange = {},
      label = label,
      readOnly = true,
      enabled = false,
      modifier = Modifier.fillMaxWidth(),
    )
    return
  }

  ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
    OutlinedTextField(
      value = form.ingredient?.name.orEmpty(),
      onValueChange = {},
      label = label,
      readOnly = true,
      singleLine = true,
      trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
      modifier = Modifier.fillMaxWidth().menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
    )
    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
      ingredients.forEach { ingredient ->
        DropdownMenuItem(
          text = { Text(ingredient.name) },
          onClick = {
            onSelected(ingredient)
            expanded = false
          },
        )
      }
    }
  }
}
