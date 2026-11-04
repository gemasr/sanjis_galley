package com.example.sanjisgalley.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sanjisgalley.R
import com.example.sanjisgalley.domain.model.RecipeAvailability

/** "Disponible" or "Faltan N ingredientes" / "Falta 1 ingrediente". */
@Composable
fun AvailabilityLabel(availability: RecipeAvailability, modifier: Modifier = Modifier) {
  val colors = MaterialTheme.colorScheme
  val (container, content) =
    if (availability.isAvailable) colors.primaryContainer to colors.onPrimaryContainer
    else colors.errorContainer to colors.onErrorContainer
  val text =
    if (availability.isAvailable) {
      stringResource(R.string.recipe_available)
    } else {
      val count = availability.missing.size
      pluralStringResource(R.plurals.recipe_missing_count, count, count)
    }
  Surface(modifier = modifier, shape = MaterialTheme.shapes.small, color = container, contentColor = content) {
    Text(
      text = text,
      style = MaterialTheme.typography.labelLarge,
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
    )
  }
}
