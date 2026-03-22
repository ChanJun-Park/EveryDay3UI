package com.jingom.everyday.three.main.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jingom.everyday.three.main.logic.BottomNavItem

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    // 탭 항목 목록
    val items =
        listOf(
            BottomNavItem.Home,
            BottomNavItem.Calendar,
            BottomNavItem.Profile,
        )

    // 현재 백스택의 엔트리를 State로 관찰
    // navController.currentBackStackEntryAsState(): 현재 화면이 바뀔 때마다 recomposition 트리거
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        modifier = modifier,
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        // 백스택에 동일한 화면이 중복으로 쌓이지 않도록
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true // 이전 탭 상태 저장
                        }
                        launchSingleTop = true // 동일 탭 재클릭 시 새 인스턴스 생성 방지
                        restoreState = true // 저장된 탭 상태 복원
                    }
                },
                icon = {
                    // 선택 여부에 따라 아이콘 전환
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                    )
                },
                label = { Text(text = item.label) },
                colors =
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
            )
        }
    }
}
