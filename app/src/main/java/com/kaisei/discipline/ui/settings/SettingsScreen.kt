package com.kaisei.discipline.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaisei.discipline.ui.ScrollPanel
import com.kaisei.discipline.viewmodel.AppViewModelFactory
import com.kaisei.discipline.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(factory: AppViewModelFactory, onResetComplete: () -> Unit) {
    val vm: SettingsViewModel = viewModel(factory = factory)
    val s by vm.state.collectAsState()
    var showResetConfirm by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("SETTINGS", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        ScrollPanel(modifier = Modifier.fillMaxWidth()) {
            SettingRow("Daily Notifications", s.notificationsOn, vm::setNotifications)
            SettingRow("Sound", s.soundOn, vm::setSound)
            SettingRow("Haptic Feedback", s.hapticsOn, vm::setHaptics)
        }

        Spacer(Modifier.height(16.dp))

        ScrollPanel(modifier = Modifier.fillMaxWidth()) {
            Text("RESET JOURNEY", style = MaterialTheme.typography.titleLarge)
            Text(
                "The journey isn't over. Begin again.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(8.dp))
            Button(onClick = { showResetConfirm = true }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                Text("RESET")
            }
        }

        Spacer(Modifier.height(16.dp))

        ScrollPanel(modifier = Modifier.fillMaxWidth()) {
            Text("ABOUT", style = MaterialTheme.typography.titleLarge)
            Text("KAISEI — Discipline Beyond Desire", style = MaterialTheme.typography.bodyMedium)
            Text("Version 1.0.0", style = MaterialTheme.typography.labelSmall)
            Text(
                "100% offline. No account, no analytics, no cloud dependency.",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }

    if (showResetConfirm) {
        AlertDialog(
            onDismissRequest = { showResetConfirm = false },
            title = { Text("Are you sure you want to reset your journey?") },
            text = { Text("Your history, longest streak, and unlocked chapters will be kept.") },
            confirmButton = {
                TextButton(onClick = {
                    showResetConfirm = false
                    vm.resetJourney(onResetComplete)
                }) { Text("RESET") }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirm = false }) { Text("CANCEL") }
            }
        )
    }
}

@Composable
private fun SettingRow(label: String, value: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Switch(checked = value, onCheckedChange = onChange)
    }
}
