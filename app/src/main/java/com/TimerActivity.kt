package com

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.ui.theme.LoveTimerTheme
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime


class TimerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoveTimerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        TimerComposable()
                    }
                }
            }
        }
    }
}

@Composable
fun TimerComposable() {
    var timeDiff by remember { mutableStateOf(calculateTimeDiff()) }
    val updatedTimeDiff by rememberUpdatedState(timeDiff)

    LaunchedEffect(key1 = "timer") {
        while (true) {
            timeDiff = calculateTimeDiff()
            delay(1000L) // Delay of 1 second
        }
    }
    Text(
        text = updatedTimeDiff,
        modifier = Modifier,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF690BFF),
        textAlign = TextAlign.Center,
    )
}


private fun calculateTimeDiff(): String {
    val now = LocalDateTime.now()
    val first = LocalDateTime.of(LocalDate.of(2023, 6, 8), LocalTime.of(9, 30, 0))

    val duration: Duration = Duration.between(first, now)

    val days = duration.toDays()
    val hours = duration.toHours() % 24
    val minutes = duration.toMinutes() % 60
    val seconds = duration.seconds % 60

    return "$days Days : $hours Hours : $minutes Minutes : $seconds Seconds"
}