package com.waterresistantcoder.streakquest.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object Quiz : Screen("quiz_screen")

    data object Result : Screen("result_screen/{score}/{total}/{maxStreak}") {
        fun createRoute(score: Int, total: Int, maxStreak: Int): String {
            return "result_screen/$score/$total/$maxStreak"
        }
    }
}