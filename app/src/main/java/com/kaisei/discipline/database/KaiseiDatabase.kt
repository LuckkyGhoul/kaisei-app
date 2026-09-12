package com.kaisei.discipline.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        CompletedDayEntity::class,
        JournalEntryEntity::class,
        AchievementEntity::class,
        JourneyEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class KaiseiDatabase : RoomDatabase() {
    abstract fun completedDayDao(): CompletedDayDao
    abstract fun journalDao(): JournalDao
    abstract fun achievementDao(): AchievementDao
    abstract fun journeyDao(): JourneyDao

    companion object {
        @Volatile private var INSTANCE: KaiseiDatabase? = null

        fun getInstance(context: Context): KaiseiDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    KaiseiDatabase::class.java,
                    "kaisei.db"
                ).build().also { INSTANCE = it }
            }
    }
}
