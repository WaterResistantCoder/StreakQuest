package com.waterresistantcoder.streakquest.domain.usecase

import com.waterresistantcoder.streakquest.domain.model.Question
import com.waterresistantcoder.streakquest.domain.repository.QuizRepository
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val repository: QuizRepository
) {
    suspend operator fun invoke(quizUrl: String): List<Question> {
        return repository.getQuestions(quizUrl)
    }
}