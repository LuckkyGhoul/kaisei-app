package com.kaisei.discipline.data.model

/**
 * A character entry in the modular character pool.
 * imageResName points to a drawable in res/drawable that the user can replace
 * with licensed/original artwork. If the drawable is missing, the UI falls
 * back to a generated placeholder (see CharacterPlaceholder.kt) so the app
 * never crashes on a missing asset.
 */
data class KaiseiCharacter(
    val characterId: String,
    val characterName: String,
    val animeName: String,
    val archetype: String,
    val imageResName: String,
    val japanesePhrase: String,
    val translation: String,
    val appearanceWeight: Int, // higher = appears more often across a long journey
    val dialoguePool: List<String>
)

fun KaiseiCharacter.dialogueForOccurrence(occurrenceIndex: Int): String {
    if (dialoguePool.isEmpty()) return "Keep walking. That is the whole technique."
    return dialoguePool[occurrenceIndex % dialoguePool.size]
}
