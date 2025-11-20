package com.waterresistantcoder.streakquest.domain.model

import com.google.gson.annotations.SerializedName

class QuizModule (
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("questions_url")
    val questionsUrl: String
)