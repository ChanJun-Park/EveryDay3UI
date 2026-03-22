package com.jingom.everyday.three.ui.main.logic

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
class MainViewModel @Inject constructor(): ViewModel() {
	private val _initialized = MutableStateFlow(false)
	val initialized = _initialized.asStateFlow()

	init {
		viewModelScope.launch {
			delay(5000)
			_initialized.update { true }
		}
	}
}