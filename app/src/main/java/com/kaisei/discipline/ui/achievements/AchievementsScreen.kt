package com.kaisei.discipline.ui.achievements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaisei.discipline.ui.ScrollPanel
import com.kaisei.discipline.ui.SealBadge
import com.kaisei.discipline.viewmodel.AchievementsViewModel
import com.kaisei.discipline.viewmodel.AppViewModelFactory

@Composable
fun AchievementsScreen(factory: AppViewModelFactory) {
    val vm: AchievementsViewModel = viewModel(factory = factory)
    val rows by vm.rows.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("TITLES EARNED", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(rows) { row ->
                ScrollPanel(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        SealBadge(kanji = row.def.japaneseTitle.take(1), unlocked = row.unlocked)
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text("${row.def.japaneseTitle} — ${row.def.romaji}", style = MaterialTheme.typography.titleLarge)
                            Text(row.def.englishMeaning, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                if (row.unlocked) "Unlocked at Day ${row.def.requiredDays}" else "Requires Day ${row.def.requiredDays}",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }
}
