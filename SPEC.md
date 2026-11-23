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

**1. Sanji's Salmon Onigiri**: *Rice balls stuffed with salmon, perfect for the road.*
- Ingredients: Rice 300 g, Salmon 150 g, Nori seaweed 3 pcs
- Steps: 1) Cook the rice and let it cool slightly. 2) Pan-sear the salmon and flake it. 3) Shape the rice balls with a little salmon in the center. 4) Wrap them with the nori seaweed.

**2. Going Merry Potato Omelette**: *Juicy omelette with potato and onion.*
- Ingredients: Egg 4 pcs, Potato 2 pcs, Onion 1 pcs
- Steps: 1) Peel the potato and onion and slice them thinly. 2) Slowly poach the potato and onion over low heat. 3) Beat the eggs and mix them with the potatoes. 4) Set the omelette on both sides.

**3. Luffy's Giant Meat**: *Glazed chicken in abundance, just how the captain likes it.*
- Ingredients: Chicken 500 g, Soy sauce 50 ml, Onion 1 pcs
- Steps: 1) Marinate the chicken with the soy sauce. 2) Brown the chicken with the chopped onion. 3) Cook over medium heat until glazed.

**4. Baratie Ramen**: *Ramen with fish stock and egg.*
- Ingredients: Wheat noodles 200 g, Fish stock 500 ml, Egg 2 pcs, Onion 1 pcs, Soy sauce 20 ml
- Steps: 1) Heat the stock with the soy sauce. 2) Cook the noodles separately and drain them. 3) Boil the eggs for 7 minutes and peel them. 4) Assemble the bowl with noodles, stock, egg and chopped onion.

**5. Thousand Sunny Stew**: *Pork, potato and carrot stew for the whole crew.*
- Ingredients: Pork 400 g, Potato 2 pcs, Carrot 3 pcs, Onion 1 pcs, Fish stock 300 ml
- Steps: 1) Brown the chopped pork with the onion. 2) Add the diced potato and carrot. 3) Cover with the fish stock. 4) Simmer until everything is tender.

**6. Mellorine for Nami and Robin**: *Creamy ice cream, served with a Sanji pose.*
- Ingredients: Milk 500 ml, Cream 250 ml, Sugar 100 g, Egg 3 pcs
- Steps: 1) Heat the milk with the cream without letting it boil. 2) Beat the yolks with the sugar and mix them into the hot milk. 3) Cook, stirring, until thickened, then let it cool. 4) Freeze, stirring every 30 minutes.
