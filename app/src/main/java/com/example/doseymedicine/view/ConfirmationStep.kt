package com.example.doseymedicine.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConfirmationStep(
    name: String,
    frequency: String,
    date: String,
    time: String,
    inventory: Int
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
        // Hero Header
        Text(
            text = "Confirm Details",
            fontSize = 32.sp, // Large, "App-like" font size
            fontWeight = FontWeight.Black,
            color = Color.White,
            letterSpacing = (-0.5).sp
        )
        Text(
            text = "Review your schedule before saving.",
            color = Color.White.copy(alpha = 0.6f),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Glassmorphic "Receipt" Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            color = Color.White.copy(alpha = 0.12f), // Frosted glass
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.15f))
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                // Medicine Name Hero
                Text(
                    text = "MEDICINE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.5f),
                    letterSpacing = 1.sp
                )
                Text(
                    text = name.uppercase(),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                androidx.compose.material3.HorizontalDivider(
                    modifier = Modifier.padding(vertical = 20.dp),
                    color = Color.White.copy(alpha = 0.1f)
                )

                // Info Grid
                Row(modifier = Modifier.fillMaxWidth()) {
                    SummaryItem("Frequency", frequency, Modifier.weight(1f))
                    SummaryItem("Start Date", date, Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    SummaryItem("Time", time, Modifier.weight(1f))
                    SummaryItem("Current Stock", "$inventory pills", Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun SummaryItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = label.uppercase(),
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.5f),
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 17.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}