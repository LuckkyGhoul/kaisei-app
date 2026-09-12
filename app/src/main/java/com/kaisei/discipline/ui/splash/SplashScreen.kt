package com.kaisei.discipline.ui.splash

import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import com.kaisei.discipline.ui.SeigaihaStrip
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(if (visible) 1f else 0f, animationSpec = tween(700), label = "splashAlpha")

    LaunchedEffect(Unit) {
        visible = true
        delay(1400)
        onFinished()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SeigaihaStrip(modifier = Modifier.fillMaxWidth().alpha(alpha), rows = 2)
        Spacer(Modifier.height(16.dp))
        Text(
            "KAISEI",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.alpha(alpha)
        )
        Text(
            "継続は力なり",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(alpha)
        )
    }
}
