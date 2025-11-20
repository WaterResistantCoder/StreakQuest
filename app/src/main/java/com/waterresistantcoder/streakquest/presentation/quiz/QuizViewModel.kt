package com.waterresistantcoder.streakquest.presentation.quiz

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waterresistantcoder.streakquest.domain.usecase.GetQuestionsUseCase
import com.waterresistantcoder.streakquest.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(QuizState())
    val state: State<QuizState> = _state

    private val quizUrl: String = checkNotNull(savedStateHandle.get<String>(Constants.QUIZ_URL_KEY))

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val questions = getQuestionsUseCase(quizUrl)
                _state.value = _state.value.copy(
                    isLoading = false,
                    questions = questions,
                    error = if (questions.isEmpty()) "No questions found" else ""
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

    fun onEvent(event: QuizEvent) {
        when (event) {
            is QuizEvent.SelectOption -> handleAnswerSelection(event.option)
            is QuizEvent.SkipQuestion -> advanceToNextQuestion(isSkipped = true)
            is QuizEvent.SwipeQuestion -> handleSwipe(event.direction)
            is QuizEvent.RestartQuiz -> resetQuiz()
        }
    }

    private fun handleSwipe(direction: Int) {
        val currentIndex = _state.value.currentQuestionIndex
        val newIndex = (currentIndex + direction).coerceIn(0, _state.value.highestAnsweredIndex)
        if (newIndex != currentIndex) {
            navigateToQuestion(newIndex)
        }
    }

    private fun navigateToQuestion(index: Int) {
        val currentState = _state.value
        val newAnswer = currentState.answers[index]
        _state.value = currentState.copy(
            currentQuestionIndex = index,
            selectedOption = newAnswer,
            isAnswerRevealed = newAnswer != null,
        )
    }

    private fun handleAnswerSelection(selectedOption: String) {
        val currentState = _state.value
        // Prevent multiple clicks while waiting
        if (currentState.isAnswerRevealed || currentState.isQuizFinished) return

        val currentQuestion = currentState.questions[currentState.currentQuestionIndex]
        val isCorrect = selectedOption == currentQuestion.correctAnswer

        // Update Streak and Score Logic
        val newStreak = if (isCorrect) currentState.currentStreak + 1 else 0
        val newMaxStreak = maxOf(currentState.maxStreak, newStreak)
        val newScore = if (isCorrect) currentState.score + 1 else currentState.score
        val newAnswers = _state.value.answers.toMutableMap()
        newAnswers[currentState.currentQuestionIndex] = selectedOption

        _state.value = currentState.copy(
            selectedOption = selectedOption,
            isAnswerRevealed = true,
            score = newScore,
            currentStreak = newStreak,
            maxStreak = newMaxStreak,
            answers = newAnswers
        )

        // Wait 2 seconds then advance
        viewModelScope.launch {
            delay(Constants.QUESTION_TRANSITION_DELAY)
            advanceToNextQuestion()
        }
    }

    private fun advanceToNextQuestion(isSkipped: Boolean = false) {
        val currentState = _state.value
        val nextIndex = currentState.currentQuestionIndex + 1

        val newAnswers = _state.value.answers.toMutableMap()
        if (isSkipped && !_state.value.answers.containsKey(currentState.currentQuestionIndex)) {
            newAnswers[currentState.currentQuestionIndex] = null
        }

        if (nextIndex < currentState.questions.size) {
            _state.value = currentState.copy(
                currentQuestionIndex = nextIndex,
                highestAnsweredIndex = maxOf(nextIndex, currentState.highestAnsweredIndex),
                selectedOption = null,
                isAnswerRevealed = false,
                answers = newAnswers
            )
        } else {
            _state.value = currentState.copy(
                isQuizFinished = true,
                answers = newAnswers
            )
        }
    }

    private fun resetQuiz() {
        _state.value = QuizState()
        loadQuestions()
    }
}

sealed class QuizEvent {
    data class SelectOption(val option: String) : QuizEvent()
    data class SwipeQuestion(val direction: Int) : QuizEvent() // -1 for left, 1 for right
    data object SkipQuestion : QuizEvent()
    data object RestartQuiz : QuizEvent()
}