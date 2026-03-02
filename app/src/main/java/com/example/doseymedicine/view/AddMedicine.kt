package com.example.doseymedicine.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Dialog
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun AddMedicine(
    viewModel: MedicineViewModel,
    onBack: () -> Unit
) {
    var step by remember { mutableIntStateOf(0) }

    var name by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("Once daily") }
    var startDate by remember { mutableStateOf("Select Date") }
    var reminderTime by remember { mutableStateOf("08:00 AM") }
    var inventory by remember { mutableIntStateOf(30) }
    var threshold by remember { mutableIntStateOf(10) }

    val flowGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFC8E6F0),
            Color(0xFFDCDAF0)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(flowGradient)
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 60.dp)
    ) {
        // --- PROGRESS INDICATOR ---
        LinearProgressIndicator(
            progress = (step + 1) / 5f,
            modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
            color = Color(0xFFBA68C8),
            trackColor = Color(0xFFBA68C8).copy(alpha = 0.1f)
        )

        // --- DYNAMIC CONTENT ---
        Box(modifier = Modifier.weight(1f)) {
            when (step) {
                0 -> MedicineNameStep(name) { name = it }
                1 -> FrequencyStep(frequency) { frequency = it }
                2 -> TimeStep(startDate, reminderTime, { startDate = it }, { reminderTime = it })
                3 -> InventoryStep(inventory, threshold, { inventory = it }, { threshold = it })
                4 -> ConfirmationStep(name, frequency, startDate, reminderTime, inventory)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // --- NAVIGATION BUTTONS ---
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back/Cancel Button
            TextButton(onClick = { if (step > 0) step-- else onBack() }) {
                Text(if (step == 0) "Cancel" else "Back",
                    color = Color.Black.copy(alpha = 0.8f)
                )
            }

            // Next/Save Button
            Button(
                onClick = {
                    if (step < 4) {
                        if (step == 0 && name.isEmpty()) return@Button
                        step++
                    } else {
                        viewModel.addMedicine(
                            name = name,
                            desc = "Prescription",
                            dosage = "1 pill",
                            time = reminderTime,
                            frequency = frequency,
                            startDate = startDate,
                            endDate = "Ongoing",
                            totalPills = inventory,
                            pillsLeft = inventory
                        ) { success, _ ->
                            if (success) onBack()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Magenta
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(if (step == 4) "Save Medicine" else "Next")
            }
        }
    }
}