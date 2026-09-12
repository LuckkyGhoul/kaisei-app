package com.kaisei.discipline.repository

import android.content.Context
import com.kaisei.discipline.data.CharacterScheduler
import com.kaisei.discipline.data.model.AchievementCatalog
import com.kaisei.discipline.data.model.KaiseiCharacter
import com.kaisei.discipline.data.model.dialogueForOccurrence
import com.kaisei.discipline.data.model.LevelSystem
import com.kaisei.discipline.database.*
import com.kaisei.discipline.utils.DateTimeUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

data class UnlockResult(
    val newlyCompletedDays: List<Int>,   // e.g. [18] or [18,19] if app was closed for 2+ days
    val latestDay: Int,
    val latestCharacter: KaiseiCharacter,
    val latestDialogue: String,
    val newAchievements: List<com.kaisei.discipline.data.model.AchievementDef>
)

/** Single source of truth for the streak, journeys, characters, achievements and XP. */
class StreakRepository(context: Context) {

    private val db = KaiseiDatabase.getInstance(context)
    private val journeyDao = db.journeyDao()
    private val dayDao = db.completedDayDao()
    private val achievementDao = db.achievementDao()
    private val journalDao = db.journalDao()
    val settings = SettingsDataStore(context)

    suspend fun getOrCreateActiveJourney(): JourneyEntity {
        journeyDao.getActive()?.let { return it }
        val id = journeyDao.insert(
            JourneyEntity(
                startEpochMillis = System.currentTimeMillis(),
                endEpochMillis = null,
                finalStreak = 0,
                isActive = true
            )
        )
        return journeyDao.getActive() ?: JourneyEntity(id, System.currentTimeMillis(), null, 0, true)
    }

    fun observeActiveJourney(): Flow<JourneyEntity?> = journeyDao.observeActive()
    fun observeHistory(): Flow<List<JourneyEntity>> = journeyDao.observeHistory()
    fun observeJournal(): Flow<List<JournalEntryEntity>> = journalDao.observeAll()
    fun observeAchievements(): Flow<List<AchievementEntity>> = achievementDao.observeAll()
    fun observeCompletedDays(journeyId: Long): Flow<List<CompletedDayEntity>> = dayDao.observeForJourney(journeyId)

    /**
     * Call this whenever the app resumes / becomes visible, and periodically while
     * it's open. Compares elapsed real time against journey start and persists any
     * days that have completed since the last check — this is what makes the
     * streak correct even after the app was killed, the phone rebooted, or several
     * days passed with the app closed. No background service required.
     */
    suspend fun checkForNewlyCompletedDays(journey: JourneyEntity): UnlockResult? {
        val previouslyStored = dayDao.getForJourney(journey.journeyId)
        val previousStreak = previouslyStored.size
        val nowStreak = DateTimeUtils.completedDayCount(journey.startEpochMillis)
        if (nowStreak <= previousStreak) return null

        val newDayNumbers = (previousStreak + 1)..nowStreak
        var lastChar: KaiseiCharacter? = null
        var lastDialogue = ""
        for (dayNumber in newDayNumbers) {
            val character = CharacterScheduler.characterForDay(dayNumber)
            dayDao.insert(
                CompletedDayEntity(
                    dayNumber = dayNumber,
                    journeyId = journey.journeyId,
                    characterId = character.characterId,
                    completedAtEpochMillis = journey.startEpochMillis + dayNumber.toLong() * DateTimeUtils.ONE_DAY_MILLIS
                )
            )
            lastChar = character
            val occurrence = CharacterScheduler.occurrenceCountUpTo(character.characterId, dayNumber)
            lastDialogue = character.dialogueForOccurrence(occurrence)
        }

        // Update journey's live streak + longest streak bookkeeping happens via journeys table's finalStreak on active row
        journeyDao.update(journey.copy(finalStreak = nowStreak))

        // XP for each completed day
        settings.addXp(LevelSystem.XP_PER_DAY * newDayNumbers.count())

        // Achievements
        val newAchievements = AchievementCatalog.newlyUnlocked(previousStreak, nowStreak)
        newAchievements.forEach { ach ->
            achievementDao.insert(AchievementEntity(ach.id, System.currentTimeMillis()))
            settings.addXp(LevelSystem.XP_PER_ACHIEVEMENT)
        }

        val finalChar = lastChar ?: return null
        return UnlockResult(
            newlyCompletedDays = newDayNumbers.toList(),
            latestDay = nowStreak,
            latestCharacter = finalChar,
            latestDialogue = lastDialogue,
            newAchievements = newAchievements
        )
    }

    suspend fun longestStreakEver(): Int {
        val active = journeyDao.getActive()
        val historical = journeyDao.longestStreakEver() ?: 0
        return maxOf(historical, active?.finalStreak ?: 0)
    }

    suspend fun totalCompletedDaysAllTime(): Int = dayDao.totalCompletedDays()

    suspend fun addJournalEntry(dayNumber: Int, journeyId: Long, trigger: String, handled: String, proudOf: String, improve: String) {
        journalDao.insert(
            JournalEntryEntity(
                dayNumber = dayNumber,
                journeyId = journeyId,
                dateEpochMillis = System.currentTimeMillis(),
                trigger = trigger,
                handled = handled,
                proudOf = proudOf,
                improve = improve
            )
        )
    }

    /** Ends the current journey (keeping it in history) and starts a brand new one. */
    suspend fun resetJourney() {
        val active = journeyDao.getActive() ?: return
        journeyDao.update(active.copy(endEpochMillis = System.currentTimeMillis(), isActive = false))
        journeyDao.insert(
            JourneyEntity(
                startEpochMillis = System.currentTimeMillis(),
                endEpochMillis = null,
                finalStreak = 0,
                isActive = true
            )
        )
    }
}
