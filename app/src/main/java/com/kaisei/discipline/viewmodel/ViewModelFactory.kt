package com.kaisei.discipline.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaisei.discipline.repository.StreakRepository

/** Simple manual DI (no Hilt/Dagger needed for a prototype this size). */
class AppViewModelFactory(private val appContext: Context) : ViewModelProvider.Factory {
    private val repository by lazy { StreakRepository(appContext.applicationContext) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(repository) as T
            ChaptersViewModel::class.java -> ChaptersViewModel(repository) as T
            AchievementsViewModel::class.java -> AchievementsViewModel(repository) as T
            StatisticsViewModel::class.java -> StatisticsViewModel(repository) as T
            JournalViewModel::class.java -> JournalViewModel(repository) as T
            SettingsViewModel::class.java -> SettingsViewModel(repository) as T
            OnboardingViewModel::class.java -> OnboardingViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
