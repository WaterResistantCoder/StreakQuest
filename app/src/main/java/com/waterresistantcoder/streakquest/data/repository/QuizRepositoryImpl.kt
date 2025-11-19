package com.waterresistantcoder.streakquest.data.repository

import android.util.Log
import com.waterresistantcoder.streakquest.data.mapper.toDomainQuestion
import com.waterresistantcoder.streakquest.data.remote.QuizApi
import com.waterresistantcoder.streakquest.domain.model.Question
import com.waterresistantcoder.streakquest.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val api: QuizApi
) : QuizRepository {

    override suspend fun getQuestions(): List<Question> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getQuestions()
                response.map { it.toDomainQuestion() }
            } catch (e: Exception) {
                Log.e("QuizRepository", "Error fetching questions", e)
                emptyList()
            }
        }
    }
}