package com.waterresistantcoder.streakquest.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.waterresistantcoder.streakquest.data.local.dao.QuizModuleDao
import com.waterresistantcoder.streakquest.data.local.entity.QuizModuleEntity
import com.waterresistantcoder.streakquest.data.mapper.MapConverter

@Database(entities = [QuizModuleEntity::class], version = 1)
@TypeConverters(MapConverter::class)
abstract class QuizModuleDatabase: RoomDatabase() {
    abstract fun getQuizModuleDao(): QuizModuleDao
}