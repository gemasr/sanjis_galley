package com.example.sanjisgalley.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.sanjisgalley.R
import com.example.sanjisgalley.ui.pantry.PantryScreen
import com.example.sanjisgalley.ui.recipes.RecipesScreen
import com.example.sanjisgalley.ui.shopping.ShoppingScreen

private data class TopLevelDestination(val key: NavKey, @StringRes val label: Int, val icon: ImageVector)

// Order of the bottom bar: Despensa, Recetas, Compra.
private val TOP_LEVEL_DESTINATIONS =
  listOf(
    TopLevelDestination(PantryKey, R.string.nav_pantry, Icons.Default.Kitchen),
    TopLevelDestination(RecipesKey, R.string.nav_recipes, Icons.AutoMirrored.Filled.MenuBook),
    TopLevelDestination(ShoppingKey, R.string.nav_shopping, Icons.Default.ShoppingCart),
  )

@Composable
fun AppNavigation() {
  val navigationState =
    rememberNavigationState(startRoute = RecipesKey, topLevelRoutes = TOP_LEVEL_DESTINATIONS.map { it.key })
  val navigator = remember { Navigator(navigationState) }

  val entryProvider = entryProvider {
    entry<PantryKey> { PantryScreen() }
    entry<RecipesKey> { RecipesScreen() }
    entry<ShoppingKey> { ShoppingScreen() }
  }

  Scaffold(
    bottomBar = {
      NavigationBar {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
          val label = stringResource(destination.label)
          NavigationBarItem(
            selected = destination.key == navigationState.topLevelRoute,
            onClick = { navigator.navigate(destination.key) },
            icon = { Icon(destination.icon, contentDescription = null) },
            label = { Text(label) },
          )
        }
      }
    }
  ) { innerPadding ->
    NavDisplay(
      entries = navigationState.toDecoratedEntries(entryProvider),
      onBack = { navigator.goBack() },
      modifier = Modifier.padding(innerPadding),
    )
  }
}
