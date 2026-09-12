package com.kaisei.discipline.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaisei.discipline.data.CharacterScheduler
import com.kaisei.discipline.data.model.KaiseiCharacter
import com.kaisei.discipline.data.model.dialogueForOccurrence
import com.kaisei.discipline.repository.StreakRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChapterRow(val dayNumber: Int, val unlocked: Boolean, val character: KaiseiCharacter?, val dialogue: String?)

class ChaptersViewModel(private val repo: StreakRepository) : ViewModel() {
    private val _rows = MutableStateFlow<List<ChapterRow>>(emptyList())
    val rows: StateFlow<List<ChapterRow>> = _rows.asStateFlow()

    init {
        viewModelScope.launch {
            val journey = repo.getOrCreateActiveJourney()
            repo.observeCompletedDays(journey.journeyId).collect { completed ->
                val completedMap = completed.associateBy { it.dayNumber }
                val maxDay = (completed.maxOfOrNull { it.dayNumber } ?: 0) + 5 // show a few upcoming locked rows
                _rows.value = (1..maxOf(maxDay, 5)).map { day ->
                    val record = completedMap[day]
                    if (record != null) {
                        val character = CharacterScheduler.characterForDay(day)
                        val occurrence = CharacterScheduler.occurrenceCountUpTo(character.characterId, day)
                        ChapterRow(day, true, character, character.dialogueForOccurrence(occurrence))
                    } else {
                        ChapterRow(day, false, null, null)
                    }
                }
            }
        }
    }
}
