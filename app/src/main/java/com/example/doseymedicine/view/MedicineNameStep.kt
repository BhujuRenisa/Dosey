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


@Composable
fun MedicineNameStep(name: String, onNameChange: (String) -> Unit) {
    // FillMaxSize and Center arrangement removes the "empty" feeling
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // High-end glassmorphism icon effect
        Surface(
            modifier = Modifier.size(120.dp),
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.outline_medication_24),
                contentDescription = null,
                modifier = Modifier.padding(30.dp).size(60.dp),
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Medication Name",
            fontSize = 32.sp, // Match the "App-like" header size
            fontWeight = FontWeight.Black,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Search or type the name of your medicine.",
            fontSize = 16.sp,
            color = Color.White.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Reuse your Dosey style fields
        DoseyTextField(
            value = name,
            onValueChange = onNameChange,
            placeholder = "e.g. Glycomet",
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )

        // Add a small tip to fill space and help the user
        Text(
            text = "Tip: Include the dosage (e.g. 500mg) for better tracking.",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.4f),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}