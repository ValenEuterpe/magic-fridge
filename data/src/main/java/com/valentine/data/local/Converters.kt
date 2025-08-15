package com.valentine.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.valentine.domain.Ingredient

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringToList(value: String) : List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return gson.toJson(list)
    }
    @TypeConverter
    fun fromIngredientList(value: String): List<Ingredient> {
        val listType = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toIngredientList(list: List<Ingredient>): String {
        return gson.toJson(list)
    }

}