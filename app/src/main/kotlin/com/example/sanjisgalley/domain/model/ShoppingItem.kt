package com.example.sanjisgalley.domain.model

data class ShoppingItem(
  val ingredient: Ingredient,
  val quantity: Int,
  val purchased: Boolean = false,
)
