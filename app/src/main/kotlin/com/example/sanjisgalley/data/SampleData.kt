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
  private val egg = Ingredient("egg", "Huevo", UNITS)
  private val rice = Ingredient("rice", "Arroz", GRAMS)
  private val nori = Ingredient("nori", "Alga nori", UNITS)
  private val salmon = Ingredient("salmon", "Salmón", GRAMS)
  private val chicken = Ingredient("chicken", "Pollo", GRAMS)
  private val pork = Ingredient("pork", "Cerdo", GRAMS)
  private val onion = Ingredient("onion", "Cebolla", UNITS)
  private val carrot = Ingredient("carrot", "Zanahoria", UNITS)
  private val potato = Ingredient("potato", "Patata", UNITS)
  private val soy = Ingredient("soy", "Salsa de soja", MILLILITERS)
  private val noodles = Ingredient("noodles", "Fideos de trigo", GRAMS)
  private val stock = Ingredient("stock", "Caldo de pescado", MILLILITERS)
  private val milk = Ingredient("milk", "Leche", MILLILITERS)
  private val cream = Ingredient("cream", "Nata", MILLILITERS)
  private val sugar = Ingredient("sugar", "Azúcar", GRAMS)

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
        name = "Onigiri de salmón de Sanji",
        description = "Bolas de arroz rellenas de salmón, perfectas para el camino.",
        ingredients = listOf(RecipeIngredient(rice, 300), RecipeIngredient(salmon, 150), RecipeIngredient(nori, 3)),
        steps =
          listOf(
            "Cuece el arroz y deja que se temple.",
            "Dora el salmón en la sartén y desmenúzalo.",
            "Forma las bolas de arroz con un poco de salmón en el centro.",
            "Envuélvelas con el alga nori.",
          ),
      ),
      Recipe(
        id = "tortilla",
        name = "Tortilla del Going Merry",
        description = "Tortilla jugosa de patata y cebolla.",
        ingredients = listOf(RecipeIngredient(egg, 4), RecipeIngredient(potato, 2), RecipeIngredient(onion, 1)),
        steps =
          listOf(
            "Pela la patata y la cebolla y córtalas en láminas finas.",
            "Pocha la patata y la cebolla a fuego lento.",
            "Bate los huevos y mézclalos con las patatas.",
            "Cuaja la tortilla por los dos lados.",
          ),
      ),
      Recipe(
        id = "luffy-meat",
        name = "Carne a lo grande de Luffy",
        description = "Pollo glaseado en abundancia, como le gusta al capitán.",
        ingredients = listOf(RecipeIngredient(chicken, 500), RecipeIngredient(soy, 50), RecipeIngredient(onion, 1)),
        steps =
          listOf(
            "Marina el pollo con la salsa de soja.",
            "Dora el pollo con la cebolla picada.",
            "Cocina a fuego medio hasta que quede glaseado.",
          ),
      ),
      Recipe(
        id = "ramen",
        name = "Ramen del Baratie",
        description = "Ramen con caldo de pescado y huevo.",
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
            "Calienta el caldo con la salsa de soja.",
            "Cuece los fideos aparte y escúrrelos.",
            "Hierve los huevos 7 minutos y pélalos.",
            "Monta el bol con los fideos, el caldo, el huevo y la cebolla picada.",
          ),
      ),
      Recipe(
        id = "stew",
        name = "Guiso del Thousand Sunny",
        description = "Guiso de cerdo, patata y zanahoria para toda la tripulación.",
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
            "Dora el cerdo picado con la cebolla.",
            "Añade la patata y la zanahoria en dados.",
            "Cubre con el caldo de pescado.",
            "Cocina a fuego lento hasta que todo esté tierno.",
          ),
      ),
      Recipe(
        id = "mellorine",
        name = "Mellorine para Nami y Robin",
        description = "Helado cremoso, servido con una pose de Sanji.",
        ingredients =
          listOf(
            RecipeIngredient(milk, 500),
            RecipeIngredient(cream, 250),
            RecipeIngredient(sugar, 100),
            RecipeIngredient(egg, 3),
          ),
        steps =
          listOf(
            "Calienta la leche con la nata sin que llegue a hervir.",
            "Bate las yemas con el azúcar y mézclalas con la leche caliente.",
            "Cocina removiendo hasta que espese y deja enfriar.",
            "Congela, removiendo cada 30 minutos.",
          ),
      ),
    )
}
