package com.kaisei.discipline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaisei.discipline.data.model.AchievementCatalog
import com.kaisei.discipline.data.model.AchievementDef
import com.kaisei.discipline.repository.StreakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AchievementRow(val def: AchievementDef, val unlocked: Boolean, val unlockedAt: Long?)

class AchievementsViewModel(private val repo: StreakRepository) : ViewModel() {
    private val _rows = MutableStateFlow<List<AchievementRow>>(emptyList())
    val rows: StateFlow<List<AchievementRow>> = _rows.asStateFlow()

    init {
        viewModelScope.launch {
            repo.observeAchievements().collect { unlockedList ->
                val map = unlockedList.associateBy { it.achievementId }
                _rows.value = AchievementCatalog.all.map { def ->
                    val unlocked = map[def.id]
                    AchievementRow(def, unlocked != null, unlocked?.unlockedAtEpochMillis)
                }
            }
        }
    }
}
