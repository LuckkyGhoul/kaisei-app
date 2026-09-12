package com.kaisei.discipline.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaisei.discipline.ui.ScrollPanel
import com.kaisei.discipline.ui.SeigaihaStrip
import com.kaisei.discipline.ui.SectionHeading
import com.kaisei.discipline.viewmodel.AppViewModelFactory
import com.kaisei.discipline.viewmodel.HomeViewModel

@Composable
fun HomeScreen(factory: AppViewModelFactory) {
    val vm: HomeViewModel = viewModel(factory = factory)
    val state by vm.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            SeigaihaStrip(modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            Text("KAISEI", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(
                "継続は力なり",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
            Text(
                "Consistency is strength.",
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(Modifier.height(32.dp))

            Text(text = "${state.currentStreak}", style = MaterialTheme.typography.displayLarge)
            Text(
                text = "DAYS OF DISCIPLINE",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary
            )

            Spacer(Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                StatChip(label = "Longest Streak", value = "${state.longestStreak} Days")
                StatChip(label = "Total Completed", value = "${state.totalCompletedDays} Days")
            }

            Spacer(Modifier.height(28.dp))

            ScrollPanel(modifier = Modifier.fillMaxWidth()) {
                SectionHeading("NEXT DAY")
                val c = state.countdown
                Text(
                    text = "%02d : %02d : %02d : %02d".format(c.days, c.hours, c.minutes, c.seconds),
                    style = MaterialTheme.typography.headlineMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("DAYS", "HOURS", "MINUTES", "SECONDS").forEach {
                        Text(it, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            ScrollPanel(modifier = Modifier.fillMaxWidth()) {
                SectionHeading("NEXT CHAPTER")
                Text("🔒 ???", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(4.dp))
                Text(
                    "Complete today's journey to reveal the next chapter.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(24.dp))
            SeigaihaStrip(modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(40.dp))
        }

        state.pendingUnlock?.let { unlock ->
            UnlockOverlay(unlock = unlock, onContinue = { vm.dismissUnlock() })
        }
    }
}

@Composable
private fun StatChip(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.titleLarge)
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}
