package com.example.ortho_shoulder_ai.ui.screens.progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue
import com.example.ortho_shoulder_ai.ui.theme.LightBlue

@Composable
fun ProgressScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 24.dp)
            .verticalScroll(scrollState)
    ) {
        // Header
        Text(
            text = "Progress",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Charts View
        ProgressChartsSection()
        
        Spacer(modifier = Modifier.height(80.dp)) // Nav bar space
    }
}

@Composable
fun ProgressChartsSection() {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        // ROM Improvement Chart
        ChartCard(
            title = "ROM Improvement",
            value = "140°",
            subtitle = "Active Flexion",
            icon = null
        ) {
            ROMLineChart()
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Daily Completion Chart
        ChartCard(
            title = "Daily Completion",
            value = "85%",
            subtitle = "Weekly Average",
            icon = null
        ) {
            DailyActivityChart()
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Pain Level Correlation
        ChartCard(
            title = "Pain Level",
            value = "2.4",
            subtitle = "Weekly Average",
            icon = null
        ) {
            PainLevelChart()
        }
    }
}

@Composable
fun ChartCard(
    title: String,
    value: String,
    subtitle: String,
    icon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Surface(
        color = Color(0xFFF9FBFF),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(text = title, fontSize = 14.sp, color = Color.Gray)
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = value,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkBlue
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = subtitle,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
                icon?.invoke()
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun ROMLineChart() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val points = listOf(110f, 115f, 112f, 125f, 130f, 138f, 140f)
        val maxVal = 150f
        
        val path = Path().apply {
            moveTo(0f, height - (points[0] / maxVal * height))
            points.forEachIndexed { index, point ->
                val x = index * (width / (points.size - 1))
                val y = height - (point / maxVal * height)
                lineTo(x, y)
            }
        }

        val fillPath = Path().apply {
            addPath(path)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(LightBlue.copy(alpha = 0.3f), Color.Transparent)
            )
        )
        
        drawPath(
            path = path,
            color = LightBlue,
            style = Stroke(width = 3.dp.toPx())
        )

        // Draw points
        points.forEachIndexed { index, point ->
            val x = index * (width / (points.size - 1))
            val y = height - (point / maxVal * height)
            drawCircle(
                color = Color.White,
                radius = 4.dp.toPx(),
                center = Offset(x, y)
            )
            drawCircle(
                color = LightBlue,
                radius = 4.dp.toPx(),
                center = Offset(x, y),
                style = Stroke(width = 2.dp.toPx())
            )
        }
    }
}

@Composable
fun DailyActivityChart() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val values = listOf(0.6f, 0.8f, 0.4f, 1f, 0.9f, 0.7f, 0.85f)
        val barWidth = 24.dp.toPx()
        val spacing = (width - (barWidth * values.size)) / (values.size - 1)

        values.forEachIndexed { index, value ->
            val x = index * (barWidth + spacing)
            val barHeight = value * height
            
            drawRoundRect(
                color = Color(0xFFE1E8F5),
                topLeft = Offset(x, 0f),
                size = androidx.compose.ui.geometry.Size(barWidth, height),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx())
            )

            drawRoundRect(
                color = LightBlue,
                topLeft = Offset(x, height - barHeight),
                size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx())
            )
        }
    }
}

@Composable
fun PainLevelChart() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val points = listOf(6f, 5.5f, 4f, 3f, 3.5f, 2.5f, 2.4f)
        val maxVal = 10f
        
        val path = Path().apply {
            moveTo(0f, height - (points[0] / maxVal * height))
            points.forEachIndexed { index, point ->
                val x = index * (width / (points.size - 1))
                val y = height - (point / maxVal * height)
                lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = Color(0xFFFF5252),
            style = Stroke(width = 2.dp.toPx())
        )

        points.forEachIndexed { index, point ->
            val x = index * (width / (points.size - 1))
            val y = height - (point / maxVal * height)
            drawCircle(
                color = Color(0xFFFF5252),
                radius = 3.dp.toPx(),
                center = Offset(x, y)
            )
        }
    }
}
