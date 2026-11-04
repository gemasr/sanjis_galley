package com.example.sanjisgalley.domain.model

enum class MeasureUnit(val symbol: String) {
  GRAMS("g"),
  MILLILITERS("ml"),
  UNITS("ud"),
}

data class Ingredient(
  val id: String,
  val name: String,
  val unit: MeasureUnit,
)
