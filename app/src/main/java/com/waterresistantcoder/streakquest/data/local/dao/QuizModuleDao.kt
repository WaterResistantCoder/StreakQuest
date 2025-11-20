package com.waterresistantcoder.streakquest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.waterresistantcoder.streakquest.data.local.entity.QuizModule

@Dao
interface QuizModuleDao {

    @Query("SELECT * FROM quiz_module where id = :id")
    fun getModule(id: String): QuizModule

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModule(module: QuizModule)
}