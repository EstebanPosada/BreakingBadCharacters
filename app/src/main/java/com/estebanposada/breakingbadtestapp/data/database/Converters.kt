package com.estebanposada.breakingbadtestapp.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun listOccupationToString(list: List<String>): String = gson.toJson(list)

    @TypeConverter
    fun stringToOccupations(data: String?): List<String> {
        if (data == null) {
            return emptyList()
        }
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listIntToString(list: List<Int>): String = gson.toJson(list)

    @TypeConverter
    fun stringToIntList(data: String?): List<Int> {
        if (data == null) {
            return emptyList()
        }
        val listType: Type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(data, listType)
    }
}