package com.waterresistantcoder.streakquest.presentation.quiz

import com.waterresistantcoder.streakquest.domain.model.Question

data class QuizState(
    val isLoading: Boolean = false,
    val error: String = "",

    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val highestAnsweredIndex: Int = 0,
    val answers: Map<Int, String?> = emptyMap(),

    // Game Progress
    val score: Int = 0,
    val currentStreak: Int = 0,
    val maxStreak: Int = 0,

    // Interaction State
    val selectedOption: String? = null,
    val isAnswerRevealed: Boolean = false,
    val isQuizFinished: Boolean = false
)
