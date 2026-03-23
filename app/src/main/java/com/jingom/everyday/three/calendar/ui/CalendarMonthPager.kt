package com.jingom.everyday.three.calendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.jingom.everyday.three.calendar.logic.pageToYearMonth
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue

@Composable
fun CalendarMonthPager(modifier: Modifier = Modifier) {
	val pageCount = 1200
	val initialPage = pageCount / 2

	val pagerState = rememberPagerState(
		initialPage = initialPage,
		pageCount = { pageCount },
	)

	val currentYearMonth by remember {
		derivedStateOf {
			pageToYearMonth(pagerState.currentPage, initialPage)
		}
	}

	Column(modifier) {
		MonthHeader(
			pagerState = pagerState,
			initialPage = initialPage,
			currentYearMonth = currentYearMonth,
		)

		HorizontalPager(
			state = pagerState,
			modifier = Modifier.fillMaxWidth(),
			// beyondBoundsPageCount: 현재 페이지 기준 미리 렌더링할 페이지 수
			// 1로 설정하면 좌우 한 페이지씩 미리 렌더링 → 스와이프 시 끊김 방지
			beyondViewportPageCount = 1,
		) { page ->
			val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
			val yearMonth = pageToYearMonth(page, initialPage)
			MonthCalendarGrid(
				yearMonth = yearMonth,
				modifier = Modifier
					.graphicsLayer {
						// 현재 페이지가 아닌 페이지는 약간 축소 + 투명하게 처리
						val scale = lerp(0.85f, 1f, 1f - pageOffset.absoluteValue.coerceIn(0f, 1f))
						scaleX = scale
						scaleY = scale
						alpha = lerp(0.5f, 1f, 1f - pageOffset.absoluteValue.coerceIn(0f, 1f))
					}
			)
		}
	}
}

@Composable
private fun MonthHeader(
	pagerState: PagerState,
	initialPage: Int,
	currentYearMonth: YearMonth,
	modifier: Modifier = Modifier,
) {
	val coroutineScope = rememberCoroutineScope()
	Row(
		horizontalArrangement = Arrangement.SpaceBetween,
		modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)
	) {
		// 이전 달 버튼
		IconButton(onClick = {
			coroutineScope.launch {
				// animateScrollToPage: 부드러운 애니메이션으로 페이지 이동
				pagerState.animateScrollToPage(pagerState.currentPage - 1)
			}
		}) {
			Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "이전 달")
		}

		// 현재 월 텍스트 클릭 시 오늘로 이동
		TextButton(onClick = {
			coroutineScope.launch {
				// scrollToPage: 애니메이션 없이 즉시 이동 (멀리 있을 때 유용)
				pagerState.scrollToPage(initialPage)
			}
		}) {
			Text(
				text = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy년 M월")),
				style = MaterialTheme.typography.titleMedium
			)
		}

		// 다음 달 버튼
		IconButton(onClick = {
			coroutineScope.launch {
				pagerState.animateScrollToPage(pagerState.currentPage + 1)
			}
		}) {
			Icon(Icons.Default.KeyboardArrowRight, contentDescription = "다음 달")
		}
	}
}

@Composable
private fun MonthCalendarGrid(
	yearMonth: YearMonth,
	modifier: Modifier = Modifier,
) {
	val today = LocalDate.now()
	val firstDayOfMonth = yearMonth.atDay(1)
	val daysInMonth = yearMonth.lengthOfMonth()

	// 일요일 시작: ISO DayOfWeek (MON=1..SUN=7) → 일=0, 월=1, ..., 토=6
	val startOffset = firstDayOfMonth.dayOfWeek.value % 7

	val dayLabels = listOf("일", "월", "화", "수", "목", "금", "토")
	val totalRows = (startOffset + daysInMonth + 6) / 7

	Column(
		modifier = modifier
			.fillMaxSize()
			.padding(horizontal = 16.dp),
	) {
		// 요일 헤더
		Row(modifier = Modifier.fillMaxWidth()) {
			dayLabels.forEachIndexed { index, label ->
				Box(
					modifier = Modifier.weight(1f).padding(vertical = 4.dp),
					contentAlignment = Alignment.Center,
				) {
					Text(
						text = label,
						style = MaterialTheme.typography.labelMedium,
						color = when (index) {
							0 -> Color.Red
							6 -> Color(0xFF1565C0)
							else -> MaterialTheme.colorScheme.onSurface
						},
					)
				}
			}
		}

		// 날짜 셀
		repeat(totalRows) { row ->
			Row(modifier = Modifier.fillMaxWidth()) {
				repeat(7) { col ->
					val day = row * 7 + col - startOffset + 1
					Box(
						modifier = Modifier.weight(1f).padding(2.dp),
						contentAlignment = Alignment.Center,
					) {
						if (day in 1..daysInMonth) {
							val isToday = yearMonth.atDay(day) == today
							DayCell(day = day, isToday = isToday, columnIndex = col)
						}
					}
				}
			}
		}
	}
}

@Composable
private fun DayCell(day: Int, isToday: Boolean, columnIndex: Int) {
	val textColor = when {
		isToday -> MaterialTheme.colorScheme.onPrimary
		columnIndex == 0 -> Color.Red
		columnIndex == 6 -> Color(0xFF1565C0)
		else -> MaterialTheme.colorScheme.onSurface
	}

	Box(
		modifier = Modifier
			.size(36.dp)
			.then(
				if (isToday) {
					Modifier.background(MaterialTheme.colorScheme.primary, CircleShape)
				} else {
					Modifier
				}
			),
		contentAlignment = Alignment.Center,
	) {
		Text(
			text = day.toString(),
			style = MaterialTheme.typography.bodyMedium,
			color = textColor,
		)
	}
}
