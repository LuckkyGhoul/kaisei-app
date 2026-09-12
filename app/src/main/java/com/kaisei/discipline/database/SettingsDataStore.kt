package com.kaisei.discipline.database

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "kaisei_settings")

class SettingsDataStore(private val context: Context) {

    private object Keys {
        val ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
        val NOTIFICATIONS_ON = booleanPreferencesKey("notifications_on")
        val SOUND_ON = booleanPreferencesKey("sound_on")
        val HAPTICS_ON = booleanPreferencesKey("haptics_on")
        val DARK_MODE = stringPreferencesKey("dark_mode") // "system" | "light" | "dark"
        val XP = longPreferencesKey("xp")
    }

    val onboardingDone: Flow<Boolean> = context.dataStore.data.map { it[Keys.ONBOARDING_DONE] ?: false }
    val notificationsOn: Flow<Boolean> = context.dataStore.data.map { it[Keys.NOTIFICATIONS_ON] ?: true }
    val soundOn: Flow<Boolean> = context.dataStore.data.map { it[Keys.SOUND_ON] ?: true }
    val hapticsOn: Flow<Boolean> = context.dataStore.data.map { it[Keys.HAPTICS_ON] ?: true }
    val darkMode: Flow<String> = context.dataStore.data.map { it[Keys.DARK_MODE] ?: "system" }
    val xp: Flow<Long> = context.dataStore.data.map { it[Keys.XP] ?: 0L }

    suspend fun setOnboardingDone(done: Boolean) {
        context.dataStore.edit { it[Keys.ONBOARDING_DONE] = done }
    }
    suspend fun setNotificationsOn(on: Boolean) {
        context.dataStore.edit { it[Keys.NOTIFICATIONS_ON] = on }
    }
    suspend fun setSoundOn(on: Boolean) {
        context.dataStore.edit { it[Keys.SOUND_ON] = on }
    }
    suspend fun setHapticsOn(on: Boolean) {
        context.dataStore.edit { it[Keys.HAPTICS_ON] = on }
    }
    suspend fun setDarkMode(mode: String) {
        context.dataStore.edit { it[Keys.DARK_MODE] = mode }
    }
    suspend fun addXp(amount: Long) {
        context.dataStore.edit { it[Keys.XP] = (it[Keys.XP] ?: 0L) + amount }
    }
}
