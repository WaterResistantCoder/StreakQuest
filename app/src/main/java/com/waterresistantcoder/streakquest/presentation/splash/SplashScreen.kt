package com.waterresistantcoder.streakquest.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.waterresistantcoder.streakquest.R
import com.waterresistantcoder.streakquest.presentation.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LaunchedEffect(key1 = true) {
            delay(1500)
            navController.navigate( // TODO module screen
                Screen.Quiz.createRoute(
                    url = "https://gist.githubusercontent.com/dr-samrat/0be7c28b6cb58e1c0674c11d7f0ba35d/raw/450fd602caac596a9127dc51d695c63e609542c5/jetpack_compose.json"
                )) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
        Image(
            painter = painterResource(R.drawable.ic_launcher_quiz),
            contentDescription = ""
        )
    }
}