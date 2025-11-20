package com.waterresistantcoder.streakquest.data.remote.dto

import com.google.gson.annotations.SerializedName

data class QuizModuleDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("questions_url")
    val questionsUrl: String
)