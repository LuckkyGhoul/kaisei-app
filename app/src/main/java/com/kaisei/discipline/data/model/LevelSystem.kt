package com.kaisei.discipline.data.model

data class LevelDef(val level: Int, val title: String, val xpRequired: Long)

object LevelSystem {
    const val XP_PER_DAY = 100L
    const val XP_PER_ACHIEVEMENT = 250L
    const val XP_PER_MILESTONE = 500L

    val levels: List<LevelDef> = listOf(
        LevelDef(1, "Beginner", 0),
        LevelDef(2, "Disciplined", 500),
        LevelDef(3, "Warrior", 1200),
        LevelDef(4, "Samurai", 2200),
        LevelDef(5, "Ronin", 3500),
        LevelDef(6, "Kenshi", 5200),
        LevelDef(7, "Master", 7500)
    )

    fun levelForXp(xp: Long): LevelDef =
        levels.lastOrNull { xp >= it.xpRequired } ?: levels.first()

    fun nextLevel(current: LevelDef): LevelDef? =
        levels.firstOrNull { it.xpRequired > current.xpRequired }
}
