package com.waterresistantcoder.streakquest.presentation.quizmodule

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waterresistantcoder.streakquest.domain.usecase.GetQuizModelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizModuleViewModel @Inject constructor(
    private val getQuizModelUseCase: GetQuizModelUseCase
) : ViewModel() {
    private var _state = mutableStateListOf<ListItemData>()
    val state: SnapshotStateList<ListItemData> = _state

    init {
        loadModules()
    }

    private fun loadModules() {
        viewModelScope.launch {
            val remoteModules = getQuizModelUseCase.getQuizModules()
            remoteModules.forEach {
                val dbModule = getQuizModelUseCase.getModule(it.id)
                if (dbModule != null) {
                    _state.add(
                        ListItemData(
                        id = it.id,
                        title = it.title,
                        isStarted = true,
                        overallStatus = "", // TODO
                        quizUrl = it.questionsUrl
                        )
                    )
                } else {
                    _state.add(ListItemData(
                        id = it.id,
                        title = it.title,
                        isStarted = false,
                        overallStatus = null,
                        quizUrl = it.questionsUrl
                    ))
                }
            }
        }
    }
}