package com.jingom.everyday.three.calendar.logic

import java.time.YearMonth

fun pageToYearMonth(page: Int, initialPage: Int): YearMonth {
	val today = YearMonth.now()
	val offset = page - initialPage
	return today.plusMonths(offset.toLong())
}