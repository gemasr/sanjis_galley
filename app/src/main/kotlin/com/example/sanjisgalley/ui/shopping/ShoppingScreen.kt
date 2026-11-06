package com.example.sanjisgalley.ui.shopping

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sanjisgalley.R
import com.example.sanjisgalley.SanjisGalleyApplication
import com.example.sanjisgalley.domain.model.ShoppingItem

/** Connects the ViewModel with the stateless [ShoppingScreen]. */
@Composable
fun ShoppingRoute(modifier: Modifier = Modifier) {
  val application = LocalContext.current.applicationContext as SanjisGalleyApplication
  val viewModel: ShoppingViewModel = viewModel(factory = ShoppingViewModel.Factory(application.container))
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  ShoppingScreen(uiState = uiState, onEvent = viewModel::onEvent, modifier = modifier)
}

@Composable
fun ShoppingScreen(uiState: ShoppingUiState, onEvent: (ShoppingUiEvent) -> Unit, modifier: Modifier = Modifier) {
  when {
    uiState.isLoading -> Box(modifier.fillMaxSize())
    uiState.items.isEmpty() ->
      Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(stringResource(R.string.shopping_empty), style = MaterialTheme.typography.bodyLarge)
      }
    else ->
      LazyColumn(modifier = modifier.fillMaxSize()) {
        items(uiState.items, key = { "${it.ingredient.id}-${it.purchased}" }) { item ->
          ShoppingRow(item, onPurchasedChange = { onEvent(ShoppingUiEvent.PurchasedChanged(item.ingredient.id, it)) })
          HorizontalDivider()
        }
      }
  }
}

@Composable
private fun ShoppingRow(item: ShoppingItem, onPurchasedChange: (Boolean) -> Unit) {
  ListItem(
    headlineContent = {
      Text(
        text = stringResource(R.string.quantity_format, item.ingredient.name, item.quantity, item.ingredient.unit.symbol),
        textDecoration = if (item.purchased) TextDecoration.LineThrough else TextDecoration.None,
        color = if (item.purchased) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
      )
    },
    leadingContent = { Checkbox(checked = item.purchased, onCheckedChange = null) },
    modifier = Modifier.clickable { onPurchasedChange(!item.purchased) },
  )
}
