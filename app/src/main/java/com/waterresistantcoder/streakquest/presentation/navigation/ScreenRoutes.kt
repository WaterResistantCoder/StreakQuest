package com.waterresistantcoder.streakquest.presentation.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object Module : Screen("module_screen")
    data object Quiz : Screen("quiz_screen/{id}/{quizUrl}") {
        fun createRoute(id: String, url: String): String {
            val encodedUrl = Uri.encode(url)
            return "quiz_screen/$id/$encodedUrl"
        }
    }

    data object Result : Screen("result_screen/{score}/{total}/{maxStreak}") {
        fun createRoute(score: Int, total: Int, maxStreak: Int): String {
            return "result_screen/$score/$total/$maxStreak"
        }
    }
}