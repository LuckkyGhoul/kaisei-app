package com.kaisei.discipline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaisei.discipline.database.JournalEntryEntity
import com.kaisei.discipline.repository.StreakRepository
import com.kaisei.discipline.utils.DateTimeUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JournalViewModel(private val repo: StreakRepository) : ViewModel() {
    private val _entries = MutableStateFlow<List<JournalEntryEntity>>(emptyList())
    val entries: StateFlow<List<JournalEntryEntity>> = _entries.asStateFlow()

    init {
        viewModelScope.launch {
            repo.observeJournal().collect { _entries.value = it }
        }
    }

    fun saveEntry(trigger: String, handled: String, proudOf: String, improve: String) {
        viewModelScope.launch {
            val journey = repo.getOrCreateActiveJourney()
            val day = DateTimeUtils.completedDayCount(journey.startEpochMillis)
            repo.addJournalEntry(day, journey.journeyId, trigger, handled, proudOf, improve)
        }
    }
}
