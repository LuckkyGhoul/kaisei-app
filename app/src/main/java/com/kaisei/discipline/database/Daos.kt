package com.kaisei.discipline.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletedDayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(day: CompletedDayEntity)

    @Query("SELECT * FROM completed_days WHERE journeyId = :journeyId ORDER BY dayNumber ASC")
    fun observeForJourney(journeyId: Long): Flow<List<CompletedDayEntity>>

    @Query("SELECT * FROM completed_days WHERE journeyId = :journeyId ORDER BY dayNumber ASC")
    suspend fun getForJourney(journeyId: Long): List<CompletedDayEntity>

    @Query("SELECT COUNT(*) FROM completed_days")
    suspend fun totalCompletedDays(): Int

    @Query("SELECT * FROM completed_days WHERE journeyId = :journeyId AND dayNumber = :dayNumber LIMIT 1")
    suspend fun getDay(journeyId: Long, dayNumber: Int): CompletedDayEntity?
}

@Dao
interface JournalDao {
    @Insert
    suspend fun insert(entry: JournalEntryEntity)

    @Query("SELECT * FROM journal_entries ORDER BY dateEpochMillis DESC")
    fun observeAll(): Flow<List<JournalEntryEntity>>
}

@Dao
interface AchievementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(achievement: AchievementEntity)

    @Query("SELECT * FROM achievements")
    fun observeAll(): Flow<List<AchievementEntity>>

    @Query("SELECT achievementId FROM achievements")
    suspend fun unlockedIds(): List<String>
}

@Dao
interface JourneyDao {
    @Insert
    suspend fun insert(journey: JourneyEntity): Long

    @Update
    suspend fun update(journey: JourneyEntity)

    @Query("SELECT * FROM journeys WHERE isActive = 1 LIMIT 1")
    suspend fun getActive(): JourneyEntity?

    @Query("SELECT * FROM journeys WHERE isActive = 1 LIMIT 1")
    fun observeActive(): Flow<JourneyEntity?>

    @Query("SELECT * FROM journeys WHERE isActive = 0 ORDER BY startEpochMillis DESC")
    fun observeHistory(): Flow<List<JourneyEntity>>

    @Query("SELECT MAX(finalStreak) FROM journeys")
    suspend fun longestStreakEver(): Int?
}
