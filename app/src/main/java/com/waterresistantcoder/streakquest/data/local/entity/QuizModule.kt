package com.waterresistantcoder.streakquest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.waterresistantcoder.streakquest.presentation.quiz.QuizState

@Entity(tableName = "quiz_module")
data class QuizModule(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val highestAnsweredIndex: Int,
    val answers: Map<Int, String?>,
    val score: Int
)