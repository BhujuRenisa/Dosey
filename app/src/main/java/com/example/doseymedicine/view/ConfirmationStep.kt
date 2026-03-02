package com.example.doseymedicine.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.example.doseymedicine.ui.theme.DarkBrown
import com.example.doseymedicine.ui.theme.PrimaryPurple

@Composable
fun ConfirmationStep(
    name: String,
    frequency: String,
    date: String,
    time: String,
    inventory: Int
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {

        Text(
            text = "Review & Confirm",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = DarkBrown,
            letterSpacing = (-0.5).sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Make sure everything looks correct",
            fontSize = 16.sp,
            color = DarkBrown.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            color = Color.White,
            shadowElevation = 12.dp
        ) {

            Column {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(SoftLavender)
                )

                Column(modifier = Modifier.padding(26.dp)) {

                    Text(
                        text = name.uppercase(),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = DarkBrown
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    androidx.compose.material3.HorizontalDivider(
                        color = Color(0xFFF5F5F5)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        SummaryItem(
                            label = "Frequency",
                            value = frequency,
                            modifier = Modifier.weight(1f)
                        )
                        SummaryItem(
                            label = "Start Date",
                            value = date,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        SummaryItem(
                            label = "Time",
                            value = time,
                            modifier = Modifier.weight(1f)
                        )
                        SummaryItem(
                            label = "Stock",
                            value = "$inventory pills",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Text(
            text = label.uppercase(),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBrown.copy(alpha= 0.4f),
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBrown
        )
    }
}