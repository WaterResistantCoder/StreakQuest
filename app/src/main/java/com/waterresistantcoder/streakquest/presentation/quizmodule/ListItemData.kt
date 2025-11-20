package com.waterresistantcoder.streakquest.presentation.quizmodule

data class ListItemData(
    val id: String = "",
    val title: String = "",
    val isStarted: Boolean = false,
    val overallStatus: String? = null,
    val quizUrl: String? = null
)