package com.example.sanjisgalley.ui.recipes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.sanjisgalley.R

/** Placeholder: only shows the screen title. */
@Composable
fun RecipesScreen(modifier: Modifier = Modifier) {
  Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    Text(text = stringResource(R.string.nav_recipes), style = MaterialTheme.typography.headlineMedium)
  }
}
