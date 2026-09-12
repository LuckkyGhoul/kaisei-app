package com.kaisei.discipline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaisei.discipline.data.model.KaiseiCharacter
import com.kaisei.discipline.data.model.AchievementDef
import com.kaisei.discipline.database.JourneyEntity
import com.kaisei.discipline.repository.StreakRepository
import com.kaisei.discipline.repository.UnlockResult
import com.kaisei.discipline.utils.DateTimeUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val loading: Boolean = true,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val totalCompletedDays: Int = 0,
    val countdown: DateTimeUtils.Countdown = DateTimeUtils.Countdown(0, 0, 0, 0),
    val xp: Long = 0,
    val pendingUnlock: UnlockResult? = null // non-null triggers the cinematic unlock screen
)

class HomeViewModel(private val repo: StreakRepository) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private var journey: JourneyEntity? = null

    init {
        viewModelScope.launch {
            journey = repo.getOrCreateActiveJourney()
            refreshStaticStats()
            tickLoop()
        }
        viewModelScope.launch {
            repo.settings.xp.collect { xpVal -> _state.value = _state.value.copy(xp = xpVal) }
        }
    }

    private suspend fun refreshStaticStats() {
        _state.value = _state.value.copy(
            longestStreak = repo.longestStreakEver(),
            totalCompletedDays = repo.totalCompletedDaysAllTime(),
            loading = false
        )
    }

    /** Ticks every second: updates the countdown and checks whether a new day has unlocked. */
    private suspend fun tickLoop() {
        while (true) {
            val j = journey ?: return
            val unlock = repo.checkForNewlyCompletedDays(j)
            if (unlock != null) {
                refreshStaticStats()
                _state.value = _state.value.copy(pendingUnlock = unlock, currentStreak = unlock.latestDay)
            }
            val streak = DateTimeUtils.completedDayCount(j.startEpochMillis)
            val nextUnlock = DateTimeUtils.nextUnlockEpochMillis(j.startEpochMillis, streak)
            val remaining = DateTimeUtils.remainingMillis(nextUnlock)
            _state.value = _state.value.copy(
                currentStreak = streak,
                countdown = DateTimeUtils.toCountdown(remaining)
            )
            delay(1000)
        }
    }

    fun dismissUnlock() {
        _state.value = _state.value.copy(pendingUnlock = null)
    }

    fun currentJourneyId(): Long? = journey?.journeyId
}
