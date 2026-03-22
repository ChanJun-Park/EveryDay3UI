package com.jingom.everyday.three.ui.main.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jingom.everyday.three.ui.ui.theme.EveryDay3UITheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		// ⚠️ super.onCreate() 보다 먼저 호출해야 합니다!
		val splashScreen = installSplashScreen()
		super.onCreate(savedInstanceState)

		// 초기 데이터 로딩이 완료될 때까지 Splash를 유지하는 조건 설정
		// keepOnScreen이 true인 동안 Splash가 화면에 유지됩니다
		splashScreen.setKeepOnScreenCondition {
			// 예: ViewModel의 로딩 상태가 완료되면 false 반환 → Splash 종료
			false // 여기서는 즉시 종료
		}

		// Splash가 사라질 때 커스텀 애니메이션 적용
		splashScreen.setOnExitAnimationListener { splashScreenView ->
			// 아래로 슬라이드 아웃하는 애니메이션
			val slideUp = ObjectAnimator.ofFloat(
				splashScreenView.view,
				View.TRANSLATION_Y,
				0f,
				-splashScreenView.view.height.toFloat()
			)
			slideUp.duration = 500L
			slideUp.doOnEnd { splashScreenView.remove() } // 애니메이션 완료 후 Splash 제거
			slideUp.start()
		}

		enableEdgeToEdge()
		setContent {
			EveryDay3UITheme {
				Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
					MainScreen(modifier = Modifier.padding(innerPadding))
				}
			}
		}
	}
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
	Text(
		text = "Hello $name!",
		modifier = modifier
	)
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
	// 앱 진입 시 페이드인 효과를 위한 알파 애니메이션 상태
	var visible by remember { mutableStateOf(false) }

	// 컴포저블이 화면에 나타나자마자 visible을 true로 변경
	LaunchedEffect(Unit) {
		visible = true
	}

	// AnimatedVisibility: visible 상태에 따라 부드럽게 등장/퇴장 처리
	AnimatedVisibility(
		visible = visible,
		enter = fadeIn(animationSpec = tween(durationMillis = 800)), // 0.8초 페이드인
		modifier = modifier,
	) {
		// 실제 메인 화면 콘텐츠
		Greeting("android")
	}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	EveryDay3UITheme {
		Greeting("Android")
	}
}