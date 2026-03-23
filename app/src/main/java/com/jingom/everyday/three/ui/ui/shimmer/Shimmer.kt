package com.jingom.everyday.three.ui.ui.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp

@Composable
fun rememberShimmerBrush(): Brush {
	val transition = rememberInfiniteTransition(label = "shimmer")

	val transitionAnim by transition.animateFloat(
		initialValue = -1000f,
		targetValue = 1000f,
		animationSpec = infiniteRepeatable(
			animation = tween(durationMillis = 1300, easing = LinearEasing),
			repeatMode = RepeatMode.Restart,
		),
		label = "shimmer_translate",
	)

	return Brush.linearGradient(
		colors = listOf(
			Color(0xFFE0E0E0), // 어두운 회색
			Color(0xFFF5F5F5), // 밝은 회색 (빛 반사 부분)
			Color(0xFFE0E0E0)  // 다시 어두운 회색
		),
		start = Offset(transitionAnim - 1000f, 0f),
		end = Offset(transitionAnim + 1000f, 0f),
		tileMode = TileMode.Mirror,
	)
}

@Composable
fun ShimmerBox(
	modifier: Modifier = Modifier,
	shape: Shape = RoundedCornerShape(8.dp),
) {
	val shimmerBrush = rememberShimmerBrush()

	Box(
		modifier = modifier
			.clip(shape)
			.background(shimmerBrush)
	)
}