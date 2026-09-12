package com.kaisei.discipline.data.model

data class AchievementDef(
    val id: String,
    val requiredDays: Int,
    val japaneseTitle: String,
    val romaji: String,
    val englishMeaning: String
)

object AchievementCatalog {
    val all: List<AchievementDef> = listOf(
        AchievementDef("d1", 1, "始まり", "Hajimari", "The Beginning"),
        AchievementDef("d3", 3, "見習い侍", "Minarai Samurai", "Apprentice Samurai"),
        AchievementDef("d7", 7, "七日侍", "Nanoka Samurai", "Seven-Day Samurai"),
        AchievementDef("d14", 14, "武士", "Bushi", "Warrior"),
        AchievementDef("d21", 21, "浪人", "Rōnin", "Ronin"),
        AchievementDef("d30", 30, "剣士", "Kenshi", "Swordsman"),
        AchievementDef("d45", 45, "忍耐者", "Nintai-sha", "One Who Endures"),
        AchievementDef("d60", 60, "覚醒者", "Kakusei-sha", "The Awakened"),
        AchievementDef("d90", 90, "覇者", "Hasha", "Conqueror"),
        AchievementDef("d180", 180, "不屈の者", "Fukutsu no Mono", "The Unyielding One"),
        AchievementDef("d365", 365, "無双", "Musō", "Unmatched")
    )

    fun newlyUnlocked(previousStreak: Int, newStreak: Int): List<AchievementDef> =
        all.filter { it.requiredDays > previousStreak && it.requiredDays <= newStreak }
}
