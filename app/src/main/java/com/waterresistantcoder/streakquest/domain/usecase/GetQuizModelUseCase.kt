package com.waterresistantcoder.streakquest.domain.usecase

import com.waterresistantcoder.streakquest.data.local.dao.QuizModuleDao
import com.waterresistantcoder.streakquest.data.local.entity.QuizModuleEntity
import com.waterresistantcoder.streakquest.domain.model.QuizModule
import com.waterresistantcoder.streakquest.domain.repository.QuizModelRepository
import com.waterresistantcoder.streakquest.domain.repository.QuizRepository
import javax.inject.Inject

class GetQuizModelUseCase @Inject constructor(
    private val dbDao: QuizModelRepository,
    private val repository: QuizRepository
) {
    suspend fun getModule(id: String): QuizModuleEntity? {
        return dbDao.getModule(id)
    }

    suspend fun insertModule(module: QuizModuleEntity) {
        return dbDao.insertModule(module)
    }

    suspend fun getQuizModules(): List<QuizModule> {
        return repository.getQuizModules()
    }
}