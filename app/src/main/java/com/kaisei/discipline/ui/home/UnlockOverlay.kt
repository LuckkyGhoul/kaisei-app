package com.kaisei.discipline.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kaisei.discipline.repository.UnlockResult
import com.kaisei.discipline.ui.CharacterPortrait
import kotlinx.coroutines.delay

/**
 * The "cinematic" daily unlock sequence: darken -> stamp -> DAY N COMPLETE ->
 * scroll opens -> character revealed -> dialogue reveals line by line -> CONTINUE.
 * Kept to Compose's built-in animation APIs (fade/slide) to stay dependency-light;
 * swap in Lottie/ink-brush assets later for the full ink-brush effect described
 * in the concept doc without touching any of the surrounding logic.
 */
@Composable
fun UnlockOverlay(unlock: UnlockResult, onContinue: () -> Unit) {
    var stage by remember { mutableStateOf(0) }

    LaunchedEffect(unlock) {
        stage = 0
        delay(400); stage = 1   // stamp / DAY N COMPLETE
        delay(900); stage = 2   // scroll opens, character + dialogue reveal
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.88f)),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = stage == 1, enter = fadeIn(tween(300))) {
            Text(
                "DAY ${unlock.latestDay} COMPLETE",
                color = Color(0xFFF3ECDD),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        AnimatedVisibility(
            visible = stage == 2,
            enter = fadeIn(tween(500)) + slideInVertically(tween(500)) { it / 6 }
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "DAY ${unlock.latestDay}",
                    color = Color(0xFFB8964F),
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(Modifier.height(12.dp))
                CharacterPortrait(character = unlock.latestCharacter, size = 108.dp)
                Spacer(Modifier.height(12.dp))
                Text(
                    unlock.latestCharacter.characterName,
                    color = Color(0xFFF3ECDD),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    unlock.latestCharacter.animeName,
                    color = Color(0xFFB8964F),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    "\u201C${unlock.latestDialogue}\u201D",
                    color = Color(0xFFF3ECDD),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "${unlock.latestCharacter.japanesePhrase} — ${unlock.latestCharacter.translation}",
                    color = Color(0xFF8A8580),
                    style = MaterialTheme.typography.labelSmall
                )

                if (unlock.newAchievements.isNotEmpty()) {
                    Spacer(Modifier.height(24.dp))
                    unlock.newAchievements.forEach { ach ->
                        Text(
                            "NEW TITLE UNLOCKED: ${ach.japaneseTitle} (${ach.englishMeaning})",
                            color = Color(0xFFB33A2E),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(Modifier.height(28.dp))
                Button(onClick = onContinue) { Text("CONTINUE") }
            }
        }
    }
}
