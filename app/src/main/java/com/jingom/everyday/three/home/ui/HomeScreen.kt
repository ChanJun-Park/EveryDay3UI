package com.jingom.everyday.three.home.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jingom.everyday.three.home.logic.HomeViewModel
import com.jingom.everyday.three.ui.ui.component.PostCardShimmer

@Composable
fun HomeScreen(
	modifier: Modifier = Modifier,
	viewModel: HomeViewModel = hiltViewModel(),
) {
	// 로딩 상태 관찰
	val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

	LazyColumn(modifier.fillMaxSize()) {
		if (isLoading) {
			// 로딩 중: Shimmer 아이템 5개 표시
			items(5) {
				PostCardShimmer()
			}
		} else {
			// 로딩 완료: 실제 데이터 표시
			items(5) { post ->
				Text("real data dummy")
			}
		}
	}
}
