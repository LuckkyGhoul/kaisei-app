package com.kaisei.discipline.ui.chapters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaisei.discipline.ui.CharacterPortrait
import com.kaisei.discipline.ui.ScrollPanel
import com.kaisei.discipline.viewmodel.AppViewModelFactory
import com.kaisei.discipline.viewmodel.ChapterRow
import com.kaisei.discipline.viewmodel.ChaptersViewModel

@Composable
fun ChaptersScreen(factory: AppViewModelFactory) {
    val vm: ChaptersViewModel = viewModel(factory = factory)
    val rows by vm.rows.collectAsState()
    var selected by remember { mutableStateOf<ChapterRow?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("YOUR JOURNEY", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(rows) { row ->
                ScrollPanel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = row.unlocked) { selected = row }
                ) {
                    Text("Day ${row.dayNumber}", style = MaterialTheme.typography.labelSmall)
                    if (row.unlocked && row.character != null) {
                        Text(row.character.characterName, style = MaterialTheme.typography.titleLarge)
                        Text(row.character.animeName, style = MaterialTheme.typography.bodyMedium)
                        Text("✓ UNLOCKED", style = MaterialTheme.typography.labelSmall)
                    } else {
                        Text("🔒 ???", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        }
    }

    selected?.let { row ->
        AlertDialog(
            onDismissRequest = { selected = null },
            confirmButton = { TextButton(onClick = { selected = null }) { Text("CLOSE") } },
            title = { Text("Day ${row.dayNumber} — ${row.character?.characterName}") },
            text = {
                Column {
                    Text(row.character?.animeName ?: "", style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(8.dp))
                    row.character?.let {
                        androidx.compose.foundation.layout.Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) { CharacterPortrait(character = it, size = 88.dp) }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("\u201C${row.dialogue}\u201D", style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "${row.character?.japanesePhrase} — ${row.character?.translation}",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        )
    }
}
