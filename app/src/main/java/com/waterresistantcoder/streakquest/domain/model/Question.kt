package com.waterresistantcoder.streakquest.domain.model

data class Question(
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String
)