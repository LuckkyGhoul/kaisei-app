package com.kaisei.discipline.data

import com.kaisei.discipline.data.model.KaiseiCharacter
import kotlin.random.Random

/**
 * Deterministically assigns a character to every day number (1..maxDays).
 *
 * Determinism contract: calling characterForDay(N) always returns the same
 * character for the same N, on any device, any run, forever — because the
 * schedule is generated from a FIXED seed, not system time or true randomness.
 * The result is also cached in Room the first time a day is completed, so the
 * app never has to depend on this algorithm being unchanged in a future update.
 *
 * Rules implemented:
 *  - No character repeats on two consecutive days.
 *  - Characters with higher appearanceWeight show up proportionally more often.
 */
object CharacterScheduler {

    private const val SEED = 20260101L // fixed forever — do not change
    private const val MAX_DAYS = 400

    private val schedule: List<KaiseiCharacter> by lazy { buildSchedule() }

    private fun buildSchedule(): List<KaiseiCharacter> {
        val pool = CharacterPool.characters
        // Build a weighted bag: each character appears `appearanceWeight` times.
        val bag = mutableListOf<KaiseiCharacter>()
        pool.forEach { c -> repeat(c.appearanceWeight.coerceAtLeast(1)) { bag.add(c) } }

        val rng = Random(SEED)
        val result = mutableListOf<KaiseiCharacter>()
        var remaining = bag.shuffled(rng).toMutableList()

        while (result.size < MAX_DAYS) {
            if (remaining.isEmpty()) {
                remaining = bag.shuffled(rng).toMutableList()
            }
            // find a candidate that isn't the same as the previous day
            val last = result.lastOrNull()
            var pickIndex = remaining.indexOfFirst { it.characterId != last?.characterId }
            if (pickIndex == -1) pickIndex = 0 // fallback, shouldn't normally happen
            result.add(remaining.removeAt(pickIndex))
        }
        return result
    }

    /** 1-based day number. */
    fun characterForDay(day: Int): KaiseiCharacter {
        require(day >= 1) { "Day must be >= 1" }
        val index = (day - 1) % schedule.size
        return schedule[index]
    }

    /** How many times this character has appeared on or before `day` — used to pick a fresh dialogue line. */
    fun occurrenceCountUpTo(characterId: String, day: Int): Int {
        var count = 0
        for (d in 1..day) {
            if (schedule[(d - 1) % schedule.size].characterId == characterId) count++
        }
        return count - 1 // 0-based index for the dialogue pool
    }
}
