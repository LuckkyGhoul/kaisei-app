package com.kaisei.discipline.ui.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kaisei.discipline.database.SettingsDataStore
import com.kaisei.discipline.ui.achievements.AchievementsScreen
import com.kaisei.discipline.ui.chapters.ChaptersScreen
import com.kaisei.discipline.ui.home.HomeScreen
import com.kaisei.discipline.ui.journal.JournalScreen
import com.kaisei.discipline.ui.onboarding.OnboardingScreen
import com.kaisei.discipline.ui.settings.SettingsScreen
import com.kaisei.discipline.ui.splash.SplashScreen
import com.kaisei.discipline.ui.statistics.StatisticsScreen
import com.kaisei.discipline.viewmodel.AppViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private sealed class Dest(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    data object Home : Dest("home", "Home", Icons.Filled.Home)
    data object Chapters : Dest("chapters", "Chapters", Icons.Filled.List)
    data object Achievements : Dest("achievements", "Titles", Icons.Filled.Star)
    data object Statistics : Dest("statistics", "Stats", Icons.Filled.Info)
    data object Settings : Dest("settings", "Settings", Icons.Filled.Settings)
}

private val bottomDests = listOf(Dest.Home, Dest.Chapters, Dest.Achievements, Dest.Statistics, Dest.Settings)

@Composable
fun KaiseiApp(factory: AppViewModelFactory, settings: SettingsDataStore) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onFinished = {
                scope.launch {
                    val done = settings.onboardingDone.first()
                    val target = if (done) "main" else "onboarding"
                    navController.navigate(target) { popUpTo("splash") { inclusive = true } }
                }
            })
        }
        composable("onboarding") {
            OnboardingScreen(factory = factory, onFinished = {
                navController.navigate("main") { popUpTo("onboarding") { inclusive = true } }
            })
        }
        composable("main") {
            MainScaffold(factory = factory)
        }
    }
}

@Composable
private fun MainScaffold(factory: AppViewModelFactory) {
    val innerNav = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val backStackEntry by innerNav.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route
                bottomDests.forEach { dest ->
                    NavigationBarItem(
                        selected = currentRoute == dest.route,
                        onClick = {
                            innerNav.navigate(dest.route) {
                                popUpTo(innerNav.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(dest.icon, contentDescription = dest.label) },
                        label = { Text(dest.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = innerNav,
            startDestination = Dest.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Dest.Home.route) { HomeScreen(factory) }
            composable(Dest.Chapters.route) { ChaptersScreen(factory) }
            composable(Dest.Achievements.route) { AchievementsScreen(factory) }
            composable(Dest.Statistics.route) { StatisticsScreen(factory) }
            composable(Dest.Settings.route) {
                SettingsScreen(factory, onResetComplete = {
                    innerNav.navigate(Dest.Home.route) {
                        popUpTo(Dest.Home.route) { inclusive = true }
                    }
                })
            }
        }
    }
}
