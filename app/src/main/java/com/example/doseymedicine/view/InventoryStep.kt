package com.example.doseymedicine.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.ui.theme.PrimaryPurple
import com.example.doseymedicine.R
import com.example.doseymedicine.ui.theme.DarkBrown


@Composable
fun InventoryStep(
    inventory: Int,
    threshold: Int,
    onInventoryChange: (Int) -> Unit,
    onThresholdChange: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {

        Text(
            text = "Your Pills ",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = DarkBrown,
            letterSpacing = (-0.5).sp
        )

        Text(
            text = "Keep track of your stock so you never run out.",
            color = DarkBrown.copy(alpha = 0.3f),
            fontSize = 18.sp,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Inventory Section
        InventoryLabel(text = "Current Stock")
        PlusMinusSelector(
            value = inventory,
            onValueChange = onInventoryChange,
            unit = "pills"
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Threshold Section
        InventoryLabel(text = "Low Stock Alert")
        PlusMinusSelector(
            value = threshold,
            onValueChange = onThresholdChange,
            unit = "pills"
        )
    }
}

@Composable
fun InventoryLabel(text: String) {
    Text(
        text = text.uppercase(),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = DarkBrown.copy(alpha = 0.5f),
        letterSpacing = 1.sp,
        modifier = Modifier.padding(start = 12.dp, bottom = 8.dp)
    )
}
@Composable
fun PlusMinusSelector(
    value: Int,
    onValueChange: (Int) -> Unit,
    unit: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Minus Button
            SelectorCircleButton(
                isPlus = false,
                onClick = { if (value > 0) onValueChange(value - 1) }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = value.toString(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                    color = DarkBrown
                )
                Text(
                    text = unit,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkBrown.copy(alpha = 0.5f)
                )
            }

            SelectorCircleButton(
                isPlus = true,
                onClick = { onValueChange(value + 1) }
            )
        }
    }
}
@Composable
fun SelectorCircleButton(
    isPlus: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isPlus) SoftLavender else SoftLavender.copy(alpha = 0.15f)
    val iconTint = if (isPlus) Color.White else SoftLavender

    Surface(
        modifier = Modifier.size(56.dp),
        shape = CircleShape,
        color = backgroundColor,
        shadowElevation = if (isPlus) 6.dp else 0.dp
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = if (isPlus) Icons.Default.Add else Icons.Default.Remove,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}