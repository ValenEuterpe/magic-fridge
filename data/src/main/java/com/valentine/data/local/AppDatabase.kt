package com.valentine.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * The main database class for the application.
 *
 * @Database annotation marks this as a Room Database.
 * - entities: Lists all the @Entity classes that are part of this database.
 * - version: The version of the database. When you change the schema (e.g., add a column),
 *   you MUST increment this version number and provide a migration strategy.
 * - exportSchema: For this project, we can set this to false. For larger projects,
 *   it's a good practice to export the schema to a file for version history.
 */
@Database(
    entities = [RecipeEntity::class],
    version = 1,
    exportSchema = false
)
// We need to tell the Database about our custom TypeConverters.
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * An abstract function that returns our RecipeDao.
     * Room will generate the implementation for this method.
     * We will use this function to get access to our database operations.
     */
    abstract fun recipeDao(): RecipeDao

}