package com.valentine.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.valentine.domain.Ingredient

@Entity(tableName = "favorite_recipes")
data class RecipeEntity(
    //@PrimaryKey tells Room that 'id' is the unique identifier for each row.
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val area: String,
    val instructions: String,
    val thumbnailUrl: String,
    val tags: List<String>, // Room needs help to store lists
    val youtubeUrl: String?,
    val ingredients: List<Ingredient> // Room also needs help with this custom list

)