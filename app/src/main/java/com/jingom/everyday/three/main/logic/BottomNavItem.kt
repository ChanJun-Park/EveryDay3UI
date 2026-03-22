package com.jingom.everyday.three.main.logic

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

// 탭 항목을 sealed class로 정의: 타입 안전성 확보
sealed class BottomNavItem(
    val route: String, // 네비게이션 라우트 (화면 식별자)
    val label: String, // 탭 라벨 텍스트
    val selectedIcon: ImageVector, // 선택됐을 때 아이콘
    val unselectedIcon: ImageVector, // 선택 안 됐을 때 아이콘
) {
    data object Home : BottomNavItem(
        route = "home",
        label = "홈",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    )

    data object Calendar : BottomNavItem(
        route = "calendar",
        label = "캘린더",
        selectedIcon = Icons.Filled.DateRange,
        unselectedIcon = Icons.Outlined.DateRange,
    )

    data object Profile : BottomNavItem(
        route = "profile",
        label = "프로필",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
    )
}
