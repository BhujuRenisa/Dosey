package com.example.doseymedicine.view

import android.widget.RadioButton
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.copy
import com.example.doseymedicine.ui.theme.PrimaryPurple

val SoftLavender = Color(0xFFBA68C8)

@Composable
fun FrequencyStep(
    selectedFrequency: String,
    onFrequencySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = "Frequency",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = DarkBrown, //
            letterSpacing = (-0.5).sp
        )
        Text(
            text = "How often do you take this medication?",
            color = DarkBrown.copy(alpha = 0.6f),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        val options = listOf("Once daily", "Twice daily", "On demand")
        options.forEach { option ->
            FrequencyCard(
                title = option,
                isSelected = selectedFrequency == option,
                onClick = { onFrequencySelected(option) }
            )
        }
    }
}

@Composable
fun FrequencyCard(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color.White else Color.White
    val borderColor = if (isSelected) SoftLavender else Color.Transparent
    val textColor = if (isSelected) DarkBrown else DarkBrown.copy(alpha = 0.7f)

    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        color = backgroundColor,
        border = if (isSelected) BorderStroke(2.dp, borderColor) else null,
        shadowElevation = if (isSelected) 8.dp else 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                color = textColor,
                fontSize = 18.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )

            RadioButton(
                selected = isSelected,
                onClick = null,
                colors = RadioButtonDefaults.colors(
                    selectedColor = SoftLavender,
                    unselectedColor = DarkBrown.copy(alpha = 0.2f)
                )
            )
        }
    }
}