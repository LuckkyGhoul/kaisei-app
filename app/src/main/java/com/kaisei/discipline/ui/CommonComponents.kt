package com.kaisei.discipline.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaisei.discipline.ui.theme.MutedGold

/** A washi-paper-style bordered panel used throughout the app to frame content
 * (countdown, chapter cards, seals) in a way that reads as a scroll fragment
 * rather than a generic Material card. */
@Composable
fun ScrollPanel(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MutedGold),
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(20.dp), content = content)
    }
}

/** A small Japanese-seal-style circular/square stamp used for achievement icons. */
@Composable
fun SealBadge(kanji: String, unlocked: Boolean, size: androidx.compose.ui.unit.Dp = 56.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .background(
                color = if (unlocked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(6.dp)
            )
            .then(Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = kanji,
            color = if (unlocked) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
            fontSize = (size.value / 2.6).sp
        )
    }
}

@Composable
fun SectionHeading(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}
