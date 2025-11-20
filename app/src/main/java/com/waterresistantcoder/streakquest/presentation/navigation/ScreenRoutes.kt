package com.waterresistantcoder.streakquest.presentation.navigation

import android.net.Uri
import com.waterresistantcoder.streakquest.util.Constants

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object Quiz : Screen("quiz_screen/{quizUrl}") {
        fun createRoute(url: String): String {
            val encodedUrl = Uri.encode(url)
            return "quiz_screen/$encodedUrl"
        }
    }

    data object Result : Screen("result_screen/{score}/{total}/{maxStreak}") {
        fun createRoute(score: Int, total: Int, maxStreak: Int): String {
            return "result_screen/$score/$total/$maxStreak"
        }
    }
}