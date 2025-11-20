package com.waterresistantcoder.streakquest.data.mapper

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapConverter {

    // Converts the Map to a JSON String for storage
    @TypeConverter
    fun fromMap(value: Map<Int, String?>): String {
        return Gson().toJson(value)
    }

    // Converts the stored JSON String back into a Map
    @TypeConverter
    fun toMap(value: String): Map<Int, String?> {
        val mapType = object : TypeToken<Map<Int, String?>>() {}.type
        return Gson().fromJson(value, mapType)
    }
}