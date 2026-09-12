package com.kaisei.discipline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaisei.discipline.repository.StreakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SettingsUiState(
    val notificationsOn: Boolean = true,
    val soundOn: Boolean = true,
    val hapticsOn: Boolean = true,
    val darkMode: String = "system"
)

class SettingsViewModel(private val repo: StreakRepository) : ViewModel() {
    private val _state = MutableStateFlow(SettingsUiState())
    val state: StateFlow<SettingsUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repo.settings.notificationsOn.collect { _state.value = _state.value.copy(notificationsOn = it) }
        }
        viewModelScope.launch {
            repo.settings.soundOn.collect { _state.value = _state.value.copy(soundOn = it) }
        }
        viewModelScope.launch {
            repo.settings.hapticsOn.collect { _state.value = _state.value.copy(hapticsOn = it) }
        }
        viewModelScope.launch {
            repo.settings.darkMode.collect { _state.value = _state.value.copy(darkMode = it) }
        }
    }

    fun setNotifications(on: Boolean) = viewModelScope.launch { repo.settings.setNotificationsOn(on) }
    fun setSound(on: Boolean) = viewModelScope.launch { repo.settings.setSoundOn(on) }
    fun setHaptics(on: Boolean) = viewModelScope.launch { repo.settings.setHapticsOn(on) }
    fun setDarkMode(mode: String) = viewModelScope.launch { repo.settings.setDarkMode(mode) }

    fun resetJourney(onDone: () -> Unit) = viewModelScope.launch {
        repo.resetJourney()
        onDone()
    }
}
