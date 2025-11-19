package com.waterresistantcoder.streakquest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.waterresistantcoder.streakquest.presentation.quiz.QuizScreen
import com.waterresistantcoder.streakquest.presentation.result.ResultScreen
import com.waterresistantcoder.streakquest.presentation.splash.SplashScreen

@Composable
fun QuizNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.Quiz.route) {
            // The ViewModel is scoped to this navigation graph entry
            QuizScreen(navController = navController)
        }

        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("score") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType },
                navArgument("maxStreak") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val total = backStackEntry.arguments?.getInt("total") ?: 0
            val maxStreak = backStackEntry.arguments?.getInt("maxStreak") ?: 0

            ResultScreen(
                navController = navController,
                score = score,
                total = total,
                maxStreak = maxStreak
            )
        }
    }
}