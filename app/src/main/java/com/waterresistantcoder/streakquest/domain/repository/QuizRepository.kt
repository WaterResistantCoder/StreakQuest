package com.waterresistantcoder.streakquest.domain.repository

import com.waterresistantcoder.streakquest.domain.model.Question

interface QuizRepository {
    suspend fun getQuestions(quizUrl: String): List<Question>
}