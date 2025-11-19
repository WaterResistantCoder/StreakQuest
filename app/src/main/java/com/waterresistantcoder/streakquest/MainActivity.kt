package com.waterresistantcoder.streakquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.waterresistantcoder.streakquest.presentation.navigation.QuizNavGraph
import com.waterresistantcoder.streakquest.presentation.theme.McqQuizTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            McqQuizTheme {
                val navController = rememberNavController()
                QuizNavGraph(navController = navController)
            }
        }
    }
}