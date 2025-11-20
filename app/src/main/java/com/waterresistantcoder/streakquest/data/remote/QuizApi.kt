package com.waterresistantcoder.streakquest.data.remote

import com.waterresistantcoder.streakquest.data.remote.dto.QuestionDto
import com.waterresistantcoder.streakquest.data.remote.dto.QuizModuleDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface QuizApi {

    @GET("/dr-samrat/ee986f16da9d8303c1acfd364ece22c5/raw")
    suspend fun getQuizModules(): List<QuizModuleDto>

    @GET
    suspend fun getQuestions(@Url url: String): List<QuestionDto>
}