package com.example.sanjisgalley.data

import com.example.sanjisgalley.domain.model.Ingredient
import com.example.sanjisgalley.domain.model.MeasureUnit.GRAMS
import com.example.sanjisgalley.domain.model.MeasureUnit.MILLILITERS
import com.example.sanjisgalley.domain.model.MeasureUnit.UNITS
import com.example.sanjisgalley.domain.model.PantryItem
import com.example.sanjisgalley.domain.model.Recipe
import com.example.sanjisgalley.domain.model.RecipeIngredient

/** Sample data from SPEC.md. */
object SampleData {
  private val egg = Ingredient("egg", "Egg", UNITS)
  private val rice = Ingredient("rice", "Rice", GRAMS)
  private val nori = Ingredient("nori", "Nori seaweed", UNITS)
  private val salmon = Ingredient("salmon", "Salmon", GRAMS)
  private val chicken = Ingredient("chicken", "Chicken", GRAMS)
  private val pork = Ingredient("pork", "Pork", GRAMS)
  private val onion = Ingredient("onion", "Onion", UNITS)
  private val carrot = Ingredient("carrot", "Carrot", UNITS)
  private val potato = Ingredient("potato", "Potato", UNITS)
  private val soy = Ingredient("soy", "Soy sauce", MILLILITERS)
  private val noodles = Ingredient("noodles", "Wheat noodles", GRAMS)
  private val stock = Ingredient("stock", "Fish stock", MILLILITERS)
  private val milk = Ingredient("milk", "Milk", MILLILITERS)
  private val cream = Ingredient("cream", "Cream", MILLILITERS)
  private val sugar = Ingredient("sugar", "Sugar", GRAMS)

  val ingredients: List<Ingredient> =
    listOf(egg, rice, nori, salmon, chicken, pork, onion, carrot, potato, soy, noodles, stock, milk, cream, sugar)

  val pantry: List<PantryItem> =
    listOf(
      PantryItem(egg, 6),
      PantryItem(rice, 1000),
      PantryItem(nori, 10),
      PantryItem(salmon, 200),
      PantryItem(onion, 3),
      PantryItem(potato, 4),
      PantryItem(soy, 200),
      PantryItem(milk, 1000),
      PantryItem(sugar, 500),
    )

  val recipes: List<Recipe> =
    listOf(
      Recipe(
        id = "onigiri",
        name = "Sanji's Salmon Onigiri",
        description = "Rice balls stuffed with salmon, perfect for the road.",
        ingredients = listOf(RecipeIngredient(rice, 300), RecipeIngredient(salmon, 150), RecipeIngredient(nori, 3)),
        steps =
          listOf(
            "Cook the rice and let it cool slightly.",
            "Pan-sear the salmon and flake it.",
            "Shape the rice balls with a little salmon in the center.",
            "Wrap them with the nori seaweed.",
          ),
      ),
      Recipe(
        id = "tortilla",
        name = "Going Merry Potato Omelette",
        description = "Juicy omelette with potato and onion.",
        ingredients = listOf(RecipeIngredient(egg, 4), RecipeIngredient(potato, 2), RecipeIngredient(onion, 1)),
        steps =
          listOf(
            "Peel the potato and onion and slice them thinly.",
            "Slowly poach the potato and onion over low heat.",
            "Beat the eggs and mix them with the potatoes.",
            "Set the omelette on both sides.",
          ),
      ),
      Recipe(
        id = "luffy-meat",
        name = "Luffy's Giant Meat",
        description = "Glazed chicken in abundance, just how the captain likes it.",
        ingredients = listOf(RecipeIngredient(chicken, 500), RecipeIngredient(soy, 50), RecipeIngredient(onion, 1)),
        steps =
          listOf(
            "Marinate the chicken with the soy sauce.",
            "Brown the chicken with the chopped onion.",
            "Cook over medium heat until glazed.",
          ),
      ),
      Recipe(
        id = "ramen",
        name = "Baratie Ramen",
        description = "Ramen with fish stock and egg.",
        ingredients =
          listOf(
            RecipeIngredient(noodles, 200),
            RecipeIngredient(stock, 500),
            RecipeIngredient(egg, 2),
            RecipeIngredient(onion, 1),
            RecipeIngredient(soy, 20),
          ),
        steps =
          listOf(
            "Heat the stock with the soy sauce.",
            "Cook the noodles separately and drain them.",
            "Boil the eggs for 7 minutes and peel them.",
            "Assemble the bowl with noodles, stock, egg and chopped onion.",
          ),
      ),
      Recipe(
        id = "stew",
        name = "Thousand Sunny Stew",
        description = "Pork, potato and carrot stew for the whole crew.",
        ingredients =
          listOf(
            RecipeIngredient(pork, 400),
            RecipeIngredient(potato, 2),
            RecipeIngredient(carrot, 3),
            RecipeIngredient(onion, 1),
            RecipeIngredient(stock, 300),
          ),
        steps =
          listOf(
            "Brown the chopped pork with the onion.",
            "Add the diced potato and carrot.",
            "Cover with the fish stock.",
            "Simmer until everything is tender.",
          ),
      ),
      Recipe(
        id = "mellorine",
        name = "Mellorine for Nami and Robin",
        description = "Creamy ice cream, served with a Sanji pose.",
        ingredients =
          listOf(
            RecipeIngredient(milk, 500),
            RecipeIngredient(cream, 250),
            RecipeIngredient(sugar, 100),
            RecipeIngredient(egg, 3),
          ),
        steps =
          listOf(
            "Heat the milk with the cream without letting it boil.",
            "Beat the yolks with the sugar and mix them into the hot milk.",
            "Cook, stirring, until thickened, then let it cool.",
            "Freeze, stirring every 30 minutes.",
          ),
      ),
    )
}
