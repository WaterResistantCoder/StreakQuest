package com.waterresistantcoder.streakquest.data.repository

import android.util.Log
import com.waterresistantcoder.streakquest.data.local.database.QuizModuleDatabase
import com.waterresistantcoder.streakquest.data.local.entity.QuizModuleEntity
import com.waterresistantcoder.streakquest.domain.repository.QuizModelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuizModelRepositoryImpl @Inject constructor(
    private val db: QuizModuleDatabase
) : QuizModelRepository {

    override suspend fun getModule(id: String): QuizModuleEntity? {
        return withContext(Dispatchers.IO) {
            try {
                db.getQuizModuleDao().getModule(id)
            } catch (e: Exception) {
                Log.e("QuizModelRepository", "Error fetching modules", e)
                null
            }
        }
    }

    override suspend fun insertModule(module: QuizModuleEntity) {
        withContext(Dispatchers.IO) {
            try {
                db.getQuizModuleDao().insertModule(module)
            } catch (e: Exception) {
                Log.e("QuizModelRepository", "Error inserting module", e)
            }
        }
    }
}