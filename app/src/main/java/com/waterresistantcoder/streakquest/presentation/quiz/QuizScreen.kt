package com.waterresistantcoder.streakquest.presentation.quiz

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.waterresistantcoder.streakquest.presentation.navigation.Screen
import com.waterresistantcoder.streakquest.presentation.quiz.components.OptionButton
import com.waterresistantcoder.streakquest.presentation.quiz.components.StreakBadge
import com.waterresistantcoder.streakquest.presentation.theme.DarkBackground

@Composable
fun QuizScreen(
    navController: NavController,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val haptic = LocalHapticFeedback.current

    // Navigate to Results when finished
    LaunchedEffect(state.isQuizFinished) {
        if (state.isQuizFinished) {
            navController.navigate(
                Screen.Result.createRoute(
                    score = state.score,
                    total = state.questions.size,
                    maxStreak = state.maxStreak
                )
            ) {
                popUpTo(Screen.Quiz.route) { inclusive = true }
            }
        }
    }

    LaunchedEffect(state.isAnswerRevealed) {
        if (state.isAnswerRevealed) {
            val isCorrect = state.selectedOption == state.questions[state.currentQuestionIndex].correctAnswer
            if (isCorrect) {
                haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
            } else {
                haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.TextHandleMove)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        // Progressbar & Streak
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Question ${state.currentQuestionIndex + 1}/${state.questions.size}",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )

            // Streak Badge
            StreakBadge(streakCount = state.currentStreak)
        }

        LinearProgressIndicator(
            progress = { if (state.questions.isNotEmpty()) (state.highestAnsweredIndex + 1) / state.questions.size.toFloat() else 0f },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.error.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.error, color = Color.Red)
            }
        } else if (state.questions.isNotEmpty()) {
            // Animated Content for "Swipe" effect between questions
            AnimatedContent(
                targetState = state.currentQuestionIndex,
                transitionSpec = {
                    val enterOffset = { fullWidth: Int ->
                        if (targetState > initialState) fullWidth else -fullWidth
                    }
                    val exitOffset = { fullWidth: Int ->
                        if (targetState > initialState) -fullWidth else fullWidth
                    }
                    slideInHorizontally(animationSpec = tween(300), initialOffsetX = enterOffset) togetherWith
                            slideOutHorizontally(animationSpec = tween(300), targetOffsetX = exitOffset)
                },
                label = "QuestionAnimation",
                modifier = Modifier.pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        change.consume()
                        when {
                            dragAmount < -50 -> {
                                // Swiped left, go to next question if available
                                viewModel.onEvent(QuizEvent.SwipeQuestion(1))
                            }
                            dragAmount > 50 -> {
                                // Swiped right, go to previous question if available
                                viewModel.onEvent(QuizEvent.SwipeQuestion(-1))
                            }
                        }
                    }
                }
            ) { index ->
                // Need to check bounds because AnimatedContent might hold onto old index for a moment
                if (index < state.questions.size) {
                    val question = state.questions[index]

                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                text = question.questionText,
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 32.dp)
                            )

                            question.options.forEach { option ->
                                OptionButton(
                                    text = option,
                                    isSelected = state.selectedOption == option,
                                    isCorrectAnswer = option == question.correctAnswer,
                                    isRevealed = state.isAnswerRevealed,
                                    onClick = {  if (!state.isAnswerRevealed) viewModel.onEvent(QuizEvent.SelectOption(option)) }
                                )
                            }

                            Spacer(modifier = Modifier.height(80.dp))

                        }

                        // Skip Button
                        if (!state.isAnswerRevealed && state.currentQuestionIndex == state.highestAnsweredIndex) {
                            OutlinedButton(
                                onClick = { viewModel.onEvent(QuizEvent.SkipQuestion) },
                                modifier = Modifier.align(Alignment.BottomEnd)
                            ) {
                                Text("Skip", color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}
