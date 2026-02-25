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
import com.example.doseymedicine.ui.theme.PrimaryPurple

@Composable
fun FrequencyStep(
    selectedFrequency: String,
    onFrequencySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "How often do you take this medication?",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(28.dp))

        val options = listOf("Once daily", "Twice daily", "On demand")
        options.forEach { option ->
            FrequencyCard(
                title = option,
                isSelected = selectedFrequency == option,
                onClick = { onFrequencySelected(option) }
            )
        }

//        FrequencyCard(
//            title = "Once daily",
//            isSelected = selectedFrequency == "Once daily",
//            onClick = { onFrequencySelected("Once daily") }
//        )
//
//        FrequencyCard(
//            title = "Twice daily",
//            isSelected = selectedFrequency == "Twice daily",
//            onClick = { onFrequencySelected("Twice daily") }
//        )
//
//        FrequencyCard(
//            title = "On demand",
//            isSelected = selectedFrequency == "On demand",
//            onClick = { onFrequencySelected("On demand") }
//        )
    }
}
@Composable
fun FrequencyCard(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val backgroundColor =
        if (isSelected)
            Color.White.copy(alpha = 0.18f)
        else
            Color.White.copy(alpha = 0.08f)

    val borderColor =
        if (isSelected)
            PrimaryPurple
        else
            Color.White.copy(alpha = 0.15f)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor,
        border = BorderStroke(
            width = 1.5.dp,
            color = borderColor
        ),
        tonalElevation = if (isSelected) 6.dp else 0.dp
    ) {

        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = title,
                modifier = Modifier.weight(1f),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = PrimaryPurple,
                    unselectedColor = Color.White.copy(alpha = 0.5f)
                )
            )
        }
    }
}