# Sanji's Galley: "What can I cook today?"

A cooking app for the Thousand Sunny's cook. It shows what's in the pantry, which recipes can be made with what's there, and builds a shopping list with what's missing. Data lives in memory and is loaded from the sample data when the app starts.

> For the agent: this document is the source of truth for data, rules and texts. Implement only what you are asked for at each moment; do not work ahead. The app's visible texts are in English; the code (classes, variables, packages) follows AGENTS.md.

## Data model

| Entity | Fields |
|---|---|
| `Ingredient` | `id`, `name`, `unit` (`g`, `ml` or `pcs`) |
| `PantryItem` | `ingredient`, `quantity` (number > 0) |
| `Recipe` | `id`, `name`, `description` (one line), `steps` (ordered list), `ingredients` (ingredient + required quantity) |
| `ShoppingItem` | `ingredient`, `quantity` (to buy), `purchased` (yes/no) |

## Business rules
- Ingredient available for a recipe: the pantry quantity is greater than or equal to the required quantity (if it isn't in the pantry, it is 0).
- Recipe available: all of its ingredients are available.
- Shortfall of an ingredient: required quantity minus pantry quantity, only if positive.
- Add missing to shopping list: for each ingredient with a shortfall, add a ShoppingItem with that quantity. If the ingredient is already in the list (not purchased), add the quantity to it instead of creating a duplicate line.
- The pantry cannot have two lines for the same ingredient: adding an existing one adds to its quantity.

## UI texts (exact)

| Element | Text |
|---|---|
| Navigation destinations | `Pantry`, `Recipes`, `Shopping` |
| Add to pantry | `Add ingredient`, `Save` |
| Form labels | `Ingredient`, `Quantity` |
| Available label | `Available` |
| Missing label | `Missing N ingredients` / `Missing 1 ingredient` |
| Detail button | `Add missing to shopping list` |
| Notice after adding | `Added to the shopping list` |
| Empty states | `The pantry is empty`, `Nothing to buy` |
| Recipe detail sections | `Ingredients`, `Steps` |
| Accessibility | `Back` (description of the back arrow) |
| Optional | `Move purchased to pantry` |

Quantity format: `Name · quantity unit`, for example `Egg · 6 pcs` or `Rice · 1000 g`. In a recipe's detail, a missing ingredient is shown as `Chicken · 500 g (missing 500 g)`.

## Sample data

### Ingredients
| id | Name | Unit |
|---|---|---|
| egg | Egg | pcs |
| rice | Rice | g |
| nori | Nori seaweed | pcs |
| salmon | Salmon | g |
| chicken | Chicken | g |
| pork | Pork | g |
| onion | Onion | pcs |
| carrot | Carrot | pcs |
| potato | Potato | pcs |
| soy | Soy sauce | ml |
| noodles | Wheat noodles | g |
| stock | Fish stock | ml |
| milk | Milk | ml |
| cream | Cream | ml |
| sugar | Sugar | g |

### Initial pantry
| Ingredient | Quantity |
|---|---|
| Egg | 6 |
| Rice | 1000 |
| Nori seaweed | 10 |
| Salmon | 200 |
| Onion | 3 |
| Potato | 4 |
| Soy sauce | 200 |
| Milk | 1000 |
| Sugar | 500 |

(There is no chicken, pork, carrot, noodles, fish stock or cream.)

### Recipes

The six sample recipes (with their ingredients and steps) are in `SAMPLE_RECIPES.md`. Read that file only when you are asked to implement the recipes or the sample data; you don't need it for anything else.
