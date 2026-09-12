package com.kaisei.discipline.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaisei.discipline.data.model.KaiseiCharacter

/**
 * Renders a character's portrait. This looks for a drawable named by
 * `imageResName` (e.g. R.drawable.char_gojo) at runtime via resource-name
 * lookup; if it isn't bundled (see README — licensed art isn't included in
 * this prototype export), it falls back to a clean text-initial medallion
 * so the screen never looks broken or shows a missing-image icon.
 */
@Composable
fun CharacterPortrait(character: KaiseiCharacter, size: Dp = 96.dp, modifier: Modifier = Modifier) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val resId = remember(character.imageResName) {
        context.resources.getIdentifier(character.imageResName, "drawable", context.packageName)
    }

    Box(
        modifier = modifier
            .size(size)
            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (resId != 0) {
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = resId),
                contentDescription = character.characterName,
                modifier = Modifier.size(size).clip(CircleShape)
            )
        } else {
            Text(
                text = character.characterName.take(1),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}
