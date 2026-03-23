package com.jingom.everyday.three.home.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
	private val _isLoading = MutableStateFlow(true)
	val isLoading = _isLoading.asStateFlow()

	init {
		viewModelScope.launch {
			delay(3000)
			_isLoading.update { false }
		}
	}
}