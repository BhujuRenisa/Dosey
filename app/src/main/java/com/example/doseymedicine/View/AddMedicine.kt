package com.example.doseymedicine.View

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.ui.theme.*

class AddMedicine : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddMedicineForm()
        }
    }
}

@Composable
fun AddMedicineForm() {
    var medName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFC8E6F0), Color(0xFFDCDAF0))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(24.dp)
    ) {
        Text(
            "Add New Medicine",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryPurple,
            modifier = Modifier.padding(top = 40.dp, bottom = 24.dp)
        )

        DoseyLabel("Medicine Name")
        DoseyTextField(value = medName, onValueChange = { medName = it }, placeholder = "e.g. Amoxicillin")

        Spacer(modifier = Modifier.height(16.dp))

        DoseyLabel("Dosage")
        DoseyTextField(value = dosage, onValueChange = { dosage = it }, placeholder = "e.g. 500mg")

        Spacer(modifier = Modifier.height(16.dp))

        DoseyLabel("Reminder Time")
        DoseyTextField(value = time, onValueChange = { time = it }, placeholder = "e.g. 08:00 AM")

        Spacer(modifier = Modifier.weight(1f))

        // Save Button
        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Save Medicine", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun DoseyLabel(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = DarkText,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun DoseyTextField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = PrimaryPurple,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}