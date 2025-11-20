package com.waterresistantcoder.streakquest.presentation.quizmodule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.waterresistantcoder.streakquest.presentation.navigation.Screen
import com.waterresistantcoder.streakquest.presentation.theme.PurpleGrey80

@Composable
fun QuizModuleScreen(
    navController: NavController,
    viewModel: QuizModuleViewModel = hiltViewModel()
) {
    val state = viewModel.state

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleGrey80)
            .padding(32.dp)
    ) {
        items(state) { item ->
            ItemRow(
                item = item,
                onStatusClick = {
                    navController.navigate(
                        Screen.Quiz.createRoute(
                            id = item.id,
                            url = item.quizUrl ?: ""
                        )) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
            HorizontalDivider(thickness = 4.dp)
        }
    }
}

@Composable
fun ItemRow(
    item: ListItemData,
    onStatusClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = item.title,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (item.isStarted && item.overallStatus != null) {
            Text(
                text = item.overallStatus,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = { onStatusClick(item.id) },
            enabled = true,
            modifier = Modifier.width(300.dp).align(Alignment.CenterHorizontally)
        ) {
            val buttonText = if (item.isStarted) "Continue" else "Start"
            Text(
                text = buttonText,
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}