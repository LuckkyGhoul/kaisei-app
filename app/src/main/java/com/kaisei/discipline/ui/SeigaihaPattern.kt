package com.kaisei.discipline.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/**
 * A lightweight, dependency-free approximation of the traditional Japanese
 * "seigaiha" (青海波, blue ocean wave) repeating arc pattern, drawn with
 * Canvas so it costs nothing at runtime and needs no bitmap assets. Used as
 * a decorative strip on Home/Splash/Onboarding to keep the app from reading
 * as a generic Material screen.
 */
@Composable
fun SeigaihaStrip(modifier: Modifier = Modifier, rows: Int = 1) {
    val color = MaterialTheme.colorScheme.tertiary
    Canvas(modifier = modifier.height((18 * rows).dp)) {
        val arcDiameter = 28.dp.toPx()
        val arcRadius = arcDiameter / 2f
        var y = 0f
        repeat(rows) {
            var x = -arcRadius
            while (x < size.width + arcRadius) {
                drawArc(
                    color = color,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(x, y),
                    size = Size(arcDiameter, arcDiameter),
                    style = Stroke(width = 1.4.dp.toPx())
                )
                x += arcRadius
            }
            y += arcRadius * 0.9f
        }
    }
}
