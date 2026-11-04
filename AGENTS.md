# AGENTS.md: project conventions

## Platform
- Native Android app in Kotlin, with Jetpack Compose and Material 3.
- Android 16 (API 36): `compileSdk` and `targetSdk` set to 36. The template may generate a higher value (for example 37): after creating the project, lower both to 36 in `app/build.gradle.kts`. Keep the `minSdk` that the Android CLI template provides.
- Keep the Gradle, AGP and Kotlin versions that Android CLI generates when creating the project; do not change them unless asked. With AGP 9, Kotlin support is built in: do not add the `kotlin-android` plugin.
- A single module (`app`), organized by packages.
- Dependency versions go in the `gradle/libs.versions.toml` catalog. Compose libraries use its BOM.
- No annotation processors: do not use KSP or kapt.

## Architecture
Clean Architecture with MVVM and unidirectional data flow (UDF). Three layers inside `app`:
- `ui` (presentation): Compose screens, ViewModels and navigation. One package per screen (`ui/pantry`, `ui/recipes`, `ui/shopping`) plus `ui/navigation` and `ui/theme`.
- `domain`: `model`, `repository` (interfaces) and `usecase`. Pure Kotlin, no Android dependencies.
- `data`: `repository` (in-memory implementations), the sample data, and `di` (the manual dependency container).

Dependencies between layers: `ui` depends on `domain`, and `data` depends on `domain`. `ui` never accesses `data` directly, except to read the container from the `Application` when creating ViewModels.

### UDF in every screen
- One immutable `UiState` (data class) per screen and a `UiEvent` (sealed interface) with the user's actions.
- The `ViewModel` keeps the state in a `MutableStateFlow`, exposes it as a `StateFlow<UiState>` and has a single `onEvent(event)` function. Use cases are called inside `viewModelScope`.
- Each screen is split in two composables: a connecting one that gets the ViewModel and collects the state with `collectAsStateWithLifecycle()`, and a stateless one, `Screen(uiState, onEvent)`, that only draws and emits events. It contains no business logic.
- One-time effects (for example, the notice shown after adding the missing ingredients to the shopping list) are modeled as state that the UI consumes and acknowledges, or as an effects flow.

## Data
- Data lives in memory: there is no database and no persistence between app launches.
- Each repository interface in `domain` has an in-memory implementation in `data/repository`, backed by a `MutableStateFlow`.
- Repositories expose data with `Flow`, so the UI updates when the pantry or the shopping list changes.
- The sample data from SPEC.md is loaded when the repositories are created, so every app launch starts from the same data.

## Dependency injection
- Manual injection, with no DI library.
- An `Application` class holds a single `AppContainer` (in `data/di`) that creates the repositories once and the use cases that depend on them. Register the `Application` class in `AndroidManifest.xml`.
- ViewModels receive their use cases through the constructor. Create them with a `ViewModelProvider.Factory` that reads the container from the `Application`; if a ViewModel needs a navigation argument (for example, the recipe id), the factory also receives it. Inside a Navigation 3 entry the ViewModel creation extras do not include the `Application`, so do not read it from them: in the connecting composable, get the container from `LocalContext.current.applicationContext` and pass it to the factory.
- Use cases and repositories are plain classes that receive their dependencies through the constructor. `domain` does not depend on Android.

## Navigation
- Navigation 3: `@Serializable` destination keys that implement `NavKey`, `NavDisplay` and `entryProvider`.
- Bottom bar with three top-level destinations (Pantry, Recipes and Shopping, with the texts from SPEC.md), each with its own back stack. The recipe detail is another key that carries the recipe id.
- To scope each ViewModel to its entry, add `rememberViewModelStoreNavEntryDecorator()` to the `NavDisplay` entry decorators.
- To set up Navigation 3, use the `navigation-3` skill.

## Allowed libraries
Jetpack Compose (with its BOM), Material 3, Material icons (`material-icons-extended`, from the Compose BOM, for the navigation and button icons), Navigation 3, Lifecycle (`lifecycle-runtime-compose` and the ViewModel integration for Navigation 3), coroutines and Flow, and kotlinx.serialization only for the navigation keys. Do not add others unless asked.

## Other decisions
- Class, variable and package names in English (`Ingredient`, `PantryItem`, `Recipe`, `ShoppingItem`).
- UI texts are exactly those in SPEC.md.
- Icons: use `material-icons-extended` for the navigation bar and buttons; do not draw custom vector drawables for them.
- Edge-to-edge and Material 3 with the template's default values.

## How to work
- Before writing code, read SPEC.md and look at the current project code.
- Implement only what you are asked for at each moment; do not work ahead on other steps.
- When you finish each task, build and verify before considering it done, and tell me what you verified.
- Do not write unit tests unless asked.

## Skills
- To create the project, build it, run it on a device or emulator and inspect the UI, use the `android-cli` skill ("Use Android CLI"), which `android init` installs.
- To interact with the running app, use `android input` (`tap`, `text`, `scroll`, `swipe`, `key`) with selectors, for example `android input tap "text=<visible text>"`. Inspect the UI with `android layout` and `android screen capture`. Do not use `adb shell input` and do not write Python or other scripts to read the layout and compute coordinates: this takes priority over what the `android-cli` skill says.
- For navigation, use the `navigation-3` skill. If it isn't installed, install it with `android skills add`.

## Checking with journeys
- Journeys live in `journeys/`, at the project root, in the format described by the `android-cli` skill.
- Each journey starts from freshly seeded sample data: since data lives in memory, force-stop the app and launch it again before running it.
