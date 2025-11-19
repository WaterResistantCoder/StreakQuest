package com.waterresistantcoder.streakquest.data.mapper

import com.waterresistantcoder.streakquest.data.remote.dto.QuestionDto
import com.waterresistantcoder.streakquest.domain.model.Question

fun QuestionDto.toDomainQuestion(): Question {
    val correctAnsString = if (options.isNotEmpty() && correctOptionIndex in options.indices) {
        options[correctOptionIndex]
    } else {
        options.firstOrNull() ?: ""
    }

    val shuffledOptions = options.shuffled()

    return Question(
        id = id,
        questionText = question,
        options = shuffledOptions,
        correctAnswer = correctAnsString
    )
}