package com.waterresistantcoder.streakquest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.waterresistantcoder.streakquest.data.local.entity.QuizModuleEntity

@Dao
interface QuizModuleDao {

    @Query("SELECT * FROM quiz_module where id = :id")
    suspend fun getModule(id: String): QuizModuleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModule(module: QuizModuleEntity)
}