package com.kaisei.discipline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.kaisei.discipline.database.SettingsDataStore
import com.kaisei.discipline.ui.nav.KaiseiApp
import com.kaisei.discipline.ui.theme.KaiseiTheme
import com.kaisei.discipline.utils.NotificationScheduler
import com.kaisei.discipline.viewmodel.AppViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        NotificationScheduler.scheduleDailyReminders(applicationContext)

        setContent {
            val factory = remember { AppViewModelFactory(applicationContext) }
            val settings = remember { SettingsDataStore(applicationContext) }
            KaiseiTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    KaiseiApp(factory = factory, settings = settings)
                }
            }
        }
    }
}
