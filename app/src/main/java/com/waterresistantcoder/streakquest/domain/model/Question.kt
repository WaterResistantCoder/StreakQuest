package com.waterresistantcoder.streakquest.domain.model

/**
 * Pure domain model representing a single quiz question.
 * independent of API response structure.
 */
data class Question(
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String
)