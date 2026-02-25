package com.example.doseymedicine.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.R


val DarkBrown = Color(0xFF3E2723)
val SoftBrown = Color(0xFF5D4037).copy(alpha = 0.7f)

@Composable
fun MedicineNameStep(name: String, onNameChange: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(140.dp),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 12.dp
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_medication_24),
                contentDescription = null,
                modifier = Modifier.padding(35.dp),
                tint = Color(0xFFBA68C8)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Medication Name",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = DarkBrown,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(11.dp))


        Text(
            text = "What medicine are we taking today?",
            fontSize = 16.sp,
            color = SoftBrown, 
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        DoseyTextField(
            value = name,
            onValueChange = onNameChange,
            placeholder = "e.g. Glycomet",
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )
    }
}