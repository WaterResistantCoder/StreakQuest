package com.waterresistantcoder.streakquest.presentation.quiz.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.waterresistantcoder.streakquest.R
import com.waterresistantcoder.streakquest.util.Constants
import kotlinx.coroutines.launch

@Composable
fun StreakBadge(
    streakCount: Int,
    modifier: Modifier = Modifier
) {
    val streakThreshold = Constants.STREAK_THRESHOLD
    val progress by animateFloatAsState(
        targetValue = (streakCount.toFloat() / streakThreshold).coerceIn(0f, 1f),
        label = "StreakProgress"
    )

    val painter = painterResource(id = R.drawable.ic_fire)
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(streakCount) {
        if (streakCount > 0 && streakCount % streakThreshold == 0) {
            launch {
                rotation.animateTo(10f, animationSpec = tween(50))
                rotation.animateTo(-10f, animationSpec = tween(50))
                rotation.animateTo(10f, animationSpec = tween(50))
                rotation.animateTo(-10f, animationSpec = tween(50))
                rotation.animateTo(0f, animationSpec = tween(50))
            }
        }
    }

    Box(
        modifier = modifier
            .size(48.dp)
            .semantics { contentDescription = "Streak Badge" }
            .graphicsLayer { rotationZ = rotation.value }
            .drawBehind {
                // Draw the grayscale base image
                with(painter) {
                    draw(
                        size = this@drawBehind.size,
                        colorFilter = ColorFilter.tint(Color.Gray.copy(alpha = 0.4f))
                    )
                }

                // Draw the colored part on top, clipped to show progress
                val fillHeight = this@drawBehind.size.height * progress
                if (fillHeight > 0) {
                    clipRect(top = this@drawBehind.size.height - fillHeight) {
                        with(painter) {
                            draw(
                                size = this@drawBehind.size,
                                colorFilter = ColorFilter.tint(Color(0xFFFFC107))
                            )
                        }
                    }
                }
            }
    )
}


@Preview(showBackground = true)
@Composable
fun StreakBadgePreview() {
    StreakBadge(streakCount = 2)
}
