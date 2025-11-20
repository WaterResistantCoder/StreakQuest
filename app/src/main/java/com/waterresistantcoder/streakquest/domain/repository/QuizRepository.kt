package com.waterresistantcoder.streakquest.domain.repository

import com.waterresistantcoder.streakquest.domain.model.Question
import com.waterresistantcoder.streakquest.domain.model.QuizModule

interface QuizRepository {
    suspend fun getQuizModules(): List<QuizModule>
    suspend fun getQuestions(quizUrl: String): List<Question>
}