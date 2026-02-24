package com.example.doseymedicine.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.ui.theme.PrimaryPurple
import com.example.doseymedicine.viewmodel.MedicineViewModel
import androidx.compose.material3.AlertDialog

import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.Dialog
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineScreen (
    viewModel: MedicineViewModel,
    onBack: () -> Unit
) {
    var medName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    // Your original light gradient
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFC8E6F0), Color(0xFFDCDAF0))
    )

    var showTimePicker by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = calendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = calendar.get(Calendar.MINUTE),
        is24Hour = false
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(24.dp)
    )
    {
        // Back Button to return to Home
        IconButton(
            onClick = onBack,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryPurple)
        }

        Text(
            "Add New Medicine",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryPurple,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        DoseyLabel("Medicine Name")
        DoseyTextField(value = medName, onValueChange = { medName = it }, placeholder = "e.g. Amoxicillin")

        Spacer(modifier = Modifier.height(16.dp))

        DoseyLabel("Dosage")
        DoseyTextField(value = dosage, onValueChange = { dosage = it }, placeholder = "e.g. 500mg")

        Spacer(modifier = Modifier.height(16.dp))

        DoseyLabel("Reminder Time")

        DoseyTextField(
            value = time,
            onValueChange = {},
            placeholder = "Select time",
            modifier = Modifier.clickable {
                showTimePicker = true
            },
            readOnly = true
        )
        Spacer(modifier = Modifier.weight(1f))

        // Save Button
        Button(
            onClick = {
                if (medName.isNotEmpty() && time.isNotEmpty()) {
                    viewModel.addMedicine(medName, dosage, time) { success, message ->
                        if (success) {
                            onBack()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                "Save Medicine",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = {
                showTimePicker = false
            },
            confirmButton = {
                TextButton(onClick = {
                    val hour = timePickerState.hour
                    val minute = timePickerState.minute

                    time = String.format("%02d:%02d", hour, minute)

                    showTimePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showTimePicker = false
                }) {
                    Text("Cancel")
                }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }
}