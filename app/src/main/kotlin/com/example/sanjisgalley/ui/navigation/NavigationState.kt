package com.example.sanjisgalley.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator

/** Creates a navigation state with one back stack per top-level route. */
@Composable
fun rememberNavigationState(startRoute: NavKey, topLevelRoutes: List<NavKey>): NavigationState {
  val topLevelRoute =
    rememberSaveable(
      saver = Saver<MutableState<NavKey>, Int>(
        save = { topLevelRoutes.indexOf(it.value) },
        restore = { mutableStateOf(topLevelRoutes[it]) },
      )
    ) {
      mutableStateOf(startRoute)
    }
  val backStacks = topLevelRoutes.associateWith { key -> rememberNavBackStack(key) }
  return remember(startRoute, topLevelRoutes) { NavigationState(startRoute, topLevelRoute, backStacks) }
}

class NavigationState(
  val startRoute: NavKey,
  topLevelRoute: MutableState<NavKey>,
  val backStacks: Map<NavKey, NavBackStack<NavKey>>,
) {
  var topLevelRoute: NavKey by topLevelRoute

  @Composable
  fun toDecoratedEntries(entryProvider: (NavKey) -> NavEntry<NavKey>): List<NavEntry<NavKey>> {
    val decoratedEntries =
      backStacks.mapValues { (_, stack) ->
        val decorators =
          listOf(
            rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
            rememberViewModelStoreNavEntryDecorator<NavKey>(),
          )
        rememberDecoratedNavEntries(backStack = stack, entryDecorators = decorators, entryProvider = entryProvider)
      }
    // "Exit through home": the start route's entries are always present.
    val inUse = if (topLevelRoute == startRoute) listOf(startRoute) else listOf(startRoute, topLevelRoute)
    return inUse.flatMap { decoratedEntries[it] ?: emptyList() }
  }
}

class Navigator(private val state: NavigationState) {
  fun navigate(route: NavKey) {
    if (route in state.backStacks.keys) state.topLevelRoute = route else state.backStacks[state.topLevelRoute]?.add(route)
  }

  fun goBack() {
    val stack = state.backStacks[state.topLevelRoute] ?: error("Stack for ${state.topLevelRoute} not found")
    if (stack.last() == state.topLevelRoute) state.topLevelRoute = state.startRoute else stack.removeLastOrNull()
  }
}
