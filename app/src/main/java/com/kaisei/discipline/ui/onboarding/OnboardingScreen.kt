package com.kaisei.discipline.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaisei.discipline.ui.SeigaihaStrip
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaisei.discipline.viewmodel.AppViewModelFactory
import com.kaisei.discipline.viewmodel.OnboardingViewModel
import kotlinx.coroutines.launch

private data class OnboardPage(val title: String, val body: String)

private val pages = listOf(
    OnboardPage("KAISEI", "Discipline Beyond Desire"),
    OnboardPage("A Chapter a Day", "Every completed day unlocks another chapter."),
    OnboardPage("The Unknown Ahead", "The next character remains hidden until you earn them."),
    OnboardPage("Your Story", "Your journey. Your discipline. Your chapter.")
)

@Composable
fun OnboardingScreen(factory: AppViewModelFactory, onFinished: () -> Unit) {
    val vm: OnboardingViewModel = viewModel(factory = factory)
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        SeigaihaStrip(modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(pages[page].title, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(12.dp))
                Text(pages[page].body, style = MaterialTheme.typography.bodyLarge, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { i ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (i == pagerState.currentPage) 10.dp else 6.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        if (pagerState.currentPage == pages.lastIndex) {
            Button(
                onClick = { vm.beginJourney(onFinished) },
                modifier = Modifier.fillMaxWidth()
            ) { Text("BEGIN JOURNEY") }
        } else {
            Button(
                onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
                modifier = Modifier.fillMaxWidth()
            ) { Text("NEXT") }
        }
    }
}
