package com.waterresistantcoder.streakquest.presentation.quiz.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.waterresistantcoder.streakquest.presentation.theme.CorrectGreen
import com.waterresistantcoder.streakquest.presentation.theme.OptionNeutral
import com.waterresistantcoder.streakquest.presentation.theme.WrongRed

@Composable
fun OptionButton(
    text: String,
    isSelected: Boolean,
    isCorrectAnswer: Boolean,
    isRevealed: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isRevealed && isCorrectAnswer -> CorrectGreen
        isRevealed && isSelected && !isCorrectAnswer -> WrongRed
        else -> OptionNeutral
    }

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp),
        // Add a border if it's selected but not revealed yet (optional visual cue)
        border = if (isSelected && !isRevealed) BorderStroke(2.dp, Color.White) else null
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}