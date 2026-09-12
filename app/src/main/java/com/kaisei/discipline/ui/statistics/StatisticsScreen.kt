package com.kaisei.discipline.ui.statistics

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaisei.discipline.ui.ScrollPanel
import com.kaisei.discipline.viewmodel.AppViewModelFactory
import com.kaisei.discipline.viewmodel.StatisticsViewModel

@Composable
fun StatisticsScreen(factory: AppViewModelFactory) {
    val vm: StatisticsViewModel = viewModel(factory = factory)
    val s by vm.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("STATISTICS", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        ScrollPanel(modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                StatBlock("Current Streak", "${s.currentStreak}")
                StatBlock("Longest Streak", "${s.longestStreak}")
            }
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                StatBlock("Total Days", "${s.totalCompletedDays}")
                StatBlock("Journeys", "${s.totalJourneys}")
            }
        }

        Spacer(Modifier.height(16.dp))

        ScrollPanel(modifier = Modifier.fillMaxWidth()) {
            Text("Level ${s.levelNumber} — ${s.levelTitle}", style = MaterialTheme.typography.titleLarge)
            Text("${s.xp} XP total", style = MaterialTheme.typography.bodyMedium)
            s.xpForNextLevel?.let { needed ->
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { (s.xpIntoLevel.toFloat() / needed.toFloat()).coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxWidth()
                )
                Text("${s.xpIntoLevel} / $needed XP to next level", style = MaterialTheme.typography.labelSmall)
            }
        }

        Spacer(Modifier.height(16.dp))

        ScrollPanel(modifier = Modifier.fillMaxWidth()) {
            Text("Achievements Unlocked: ${s.achievementsUnlocked}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun StatBlock(label: String, value: String) {
    Column {
        Text(value, style = MaterialTheme.typography.titleLarge)
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}
