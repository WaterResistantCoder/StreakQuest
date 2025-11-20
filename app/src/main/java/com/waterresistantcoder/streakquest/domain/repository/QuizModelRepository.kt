package com.waterresistantcoder.streakquest.domain.repository

import com.waterresistantcoder.streakquest.data.local.entity.QuizModuleEntity

interface QuizModelRepository {
    suspend fun getModule(id: String): QuizModuleEntity?
    suspend fun insertModule(module: QuizModuleEntity)
}