package com.kaisei.discipline.ui.journal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaisei.discipline.ui.ScrollPanel
import com.kaisei.discipline.utils.DateTimeUtils
import com.kaisei.discipline.viewmodel.AppViewModelFactory
import com.kaisei.discipline.viewmodel.JournalViewModel

@Composable
fun JournalScreen(factory: AppViewModelFactory) {
    val vm: JournalViewModel = viewModel(factory = factory)
    val entries by vm.entries.collectAsState()

    var trigger by remember { mutableStateOf("") }
    var handled by remember { mutableStateOf("") }
    var proudOf by remember { mutableStateOf("") }
    var improve by remember { mutableStateOf("") }
    var showForm by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("JOURNAL", style = MaterialTheme.typography.titleLarge)
            TextButton(onClick = { showForm = !showForm }) { Text(if (showForm) "CLOSE" else "NEW ENTRY") }
        }
        Spacer(Modifier.height(12.dp))

        if (showForm) {
            ScrollPanel(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(trigger, { trigger = it }, label = { Text("What triggered you today?") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(handled, { handled = it }, label = { Text("How did you handle it?") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(proudOf, { proudOf = it }, label = { Text("What are you proud of today?") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(improve, { improve = it }, label = { Text("What will you improve tomorrow?") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(12.dp))
                Button(onClick = {
                    vm.saveEntry(trigger, handled, proudOf, improve)
                    trigger = ""; handled = ""; proudOf = ""; improve = ""
                    showForm = false
                }) { Text("SAVE ENTRY") }
            }
            Spacer(Modifier.height(16.dp))
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(entries) { entry ->
                ScrollPanel(modifier = Modifier.fillMaxWidth()) {
                    Text(DateTimeUtils.formatDate(entry.dateEpochMillis), style = MaterialTheme.typography.labelSmall)
                    Text("Day ${entry.dayNumber}", style = MaterialTheme.typography.bodyMedium)
                    if (entry.trigger.isNotBlank()) Text("Trigger: ${entry.trigger}")
                    if (entry.handled.isNotBlank()) Text("Handled: ${entry.handled}")
                    if (entry.proudOf.isNotBlank()) Text("Proud of: ${entry.proudOf}")
                    if (entry.improve.isNotBlank()) Text("Improve: ${entry.improve}")
                }
            }
        }
    }
}
