package com.jingom.everyday.three.ui.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jingom.everyday.three.ui.ui.shimmer.ShimmerBox

@Composable
fun PostCardShimmer(modifier: Modifier = Modifier) {
	Card(
		modifier = modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp, vertical = 8.dp),
		elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			ShimmerBox(
				modifier = Modifier.size(56.dp),
				shape = CircleShape,
			)

			Spacer(Modifier.width(12.dp))

			Column(Modifier.weight(1f)) {
				ShimmerBox(
					modifier = Modifier
						.fillMaxWidth(0.7f)
						.height(16.dp)
				)
				Spacer(Modifier.height(8.dp))
				ShimmerBox(
					modifier = Modifier
						.fillMaxWidth(0.5f)
						.height(12.dp)
				)
				Spacer(Modifier.height(8.dp))
				ShimmerBox(
					modifier = Modifier
						.fillMaxWidth(0.9f)
						.height(12.dp)
				)
			}
		}
	}
}