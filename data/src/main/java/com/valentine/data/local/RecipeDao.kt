package com.valentine.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// The @Dao annotation identifies this as a Data Access Object for Room.
@Dao
interface RecipeDao{
    /**
     * Inserts a recipe into the favorite_recipes table.
     * The OnConflictStrategy.REPLACE means if we try to insert a recipe with an ID
     * that already exists, Room will simply replace the old one with the new one.
     * This is useful for "upserting" (update or insert).
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(recipe: RecipeEntity) : Long


    @Query("DELETE FROM favorite_recipes WHERE id = :recipeId")
    suspend fun removeFavorite(recipeId: String) : Int

    /**
     * Retrieves all favorite recipes from the table, ordered by name.
     * This function returns a Flow. Room will automatically emit a new list
     * whenever the data in the favorite_recipes table changes. The UI can
     * observe this flow and reactively update itself.
     */
    @Query("SELECT * FROM favorite_recipes ORDER BY name ASC")
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>

    /**
     * Checks if a recipe with a specific ID exists in the table.
     * It returns a Flow<Boolean>. This will emit `true` if the recipe is a favorite,
     * and `false` if it's not. It will automatically re-emit if the status changes.
     * The `COUNT(id) > 0` part is an efficient way to check for existence.
     */
    @Query("SELECT COUNT(id) > 0 FROM favorite_recipes WHERE id = :recipeId")
    fun isFavorite(recipeId: String): Flow<Boolean>


}