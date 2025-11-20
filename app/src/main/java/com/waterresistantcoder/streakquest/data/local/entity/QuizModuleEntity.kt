package com.waterresistantcoder.streakquest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_module")
data class QuizModuleEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val highestAnsweredIndex: Int,
    val answers: Map<Int, String?>,
    val score: Int
)