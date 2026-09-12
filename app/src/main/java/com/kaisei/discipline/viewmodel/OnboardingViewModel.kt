package com.kaisei.discipline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaisei.discipline.repository.StreakRepository
import kotlinx.coroutines.launch

class OnboardingViewModel(private val repo: StreakRepository) : ViewModel() {
    fun beginJourney(onDone: () -> Unit) {
        viewModelScope.launch {
            repo.getOrCreateActiveJourney()
            repo.settings.setOnboardingDone(true)
            onDone()
        }
    }
}
