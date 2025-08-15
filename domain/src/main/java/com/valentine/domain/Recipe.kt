package com.valentine.domain

data class Recipe(
    val id: String,
    val name: String,
    val category: String,
    val area: String,
    val instructions: String,
    val thumbnailUrl: String,
    val tags: List<String>,
    val youtubeUrl: String?,
    val ingredients: List<Ingredient>
)

data class Ingredient(
    val name: String,
    val measure: String
)