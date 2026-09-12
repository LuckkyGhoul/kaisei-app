package com.kaisei.discipline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaisei.discipline.data.model.LevelSystem
import com.kaisei.discipline.repository.StreakRepository
import com.kaisei.discipline.utils.DateTimeUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StatisticsUiState(
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val totalCompletedDays: Int = 0,
    val totalJourneys: Int = 0,
    val achievementsUnlocked: Int = 0,
    val xp: Long = 0,
    val levelTitle: String = "Beginner",
    val levelNumber: Int = 1,
    val xpIntoLevel: Long = 0,
    val xpForNextLevel: Long? = null
)

class StatisticsViewModel(private val repo: StreakRepository) : ViewModel() {
    private val _state = MutableStateFlow(StatisticsUiState())
    val state: StateFlow<StatisticsUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val journey = repo.getOrCreateActiveJourney()
            val streak = DateTimeUtils.completedDayCount(journey.startEpochMillis)
            val longest = repo.longestStreakEver()
            val total = repo.totalCompletedDaysAllTime()
            var journeyCount = 0
            repo.observeHistory().collect { history -> journeyCount = history.size + 1 }
            _state.value = _state.value.copy(
                currentStreak = streak,
                longestStreak = longest,
                totalCompletedDays = total,
                totalJourneys = journeyCount
            )
        }
        viewModelScope.launch {
            repo.observeAchievements().collect {
                _state.value = _state.value.copy(achievementsUnlocked = it.size)
            }
        }
        viewModelScope.launch {
            repo.settings.xp.collect { xpVal ->
                val level = LevelSystem.levelForXp(xpVal)
                val next = LevelSystem.nextLevel(level)
                _state.value = _state.value.copy(
                    xp = xpVal,
                    levelTitle = level.title,
                    levelNumber = level.level,
                    xpIntoLevel = xpVal - level.xpRequired,
                    xpForNextLevel = next?.let { it.xpRequired - level.xpRequired }
                )
            }
        }
    }
}
