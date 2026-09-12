package com.kaisei.discipline.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/** One record per completed day within the CURRENT journey. */
@Entity(tableName = "completed_days")
data class CompletedDayEntity(
    @PrimaryKey val dayNumber: Int,
    val journeyId: Long,
    val characterId: String,
    val completedAtEpochMillis: Long
)

/** One record per journal entry. */
@Entity(tableName = "journal_entries")
data class JournalEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dayNumber: Int,
    val journeyId: Long,
    val dateEpochMillis: Long,
    val trigger: String,
    val handled: String,
    val proudOf: String,
    val improve: String
)

/** One record per unlocked achievement. */
@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val achievementId: String,
    val unlockedAtEpochMillis: Long
)

/** One record per journey (current + all historical ones). */
@Entity(tableName = "journeys")
data class JourneyEntity(
    @PrimaryKey(autoGenerate = true) val journeyId: Long = 0,
    val startEpochMillis: Long,
    val endEpochMillis: Long?, // null while active
    val finalStreak: Int,      // updated live while active; frozen once ended
    val isActive: Boolean
)
