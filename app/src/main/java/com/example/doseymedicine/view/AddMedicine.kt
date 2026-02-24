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
fun AddMedicineScreen (
    viewModel: MedicineViewModel,
    onBack: () -> Unit
) {
    var medName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var totalPills by remember { mutableStateOf("") }
    var pillsLeft by remember { mutableStateOf("") }

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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        item{
            Row (modifier =  Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)){
                Column(modifier= Modifier.weight(0.2f)){
                    IconButton(
                        onClick = onBack,
//                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PrimaryPurple)
                    }
                }
                Column(modifier= Modifier.weight(1f). padding(top= 9.dp)) {
                    Text(
                        "Add New Medicine",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryPurple
                    )
                }
            }
        }

        item {
            DoseyLabel("Medicine Name")
            DoseyTextField(
                value = medName,
                onValueChange = { medName = it },
                placeholder = "e.g. Amoxicillin"
            )
        }

        item {
            DoseyLabel("Description")
            DoseyTextField(
                value = desc,
                onValueChange = { desc = it },
                placeholder = "e.g. After food"
            )
        }

        item {
            DoseyLabel("Dosage")
            DoseyTextField(
                value = dosage,
                onValueChange = { dosage = it },
                placeholder = "e.g. 500mg"
            )
        }
//        FREQUENCY
        item {
            DoseyLabel("Frequency")
            DoseyTextField(
               value = frequency,
               onValueChange = { frequency = it },
              placeholder = "How many times a day?"
           )
       }


//        START AND END DATE FIELD
        item{
            Row (modifier =  Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)){
                Column(modifier= Modifier.weight(1f)){
                    DoseyLabel("Start Date")
                    DoseyTextField(
                        value = startDate,
                        onValueChange = { startDate = it },
                        placeholder = "DD/MM/YYYY"
                    )
                }
                Column(modifier= Modifier.weight(1f)) {
                    DoseyLabel("End Date")
                    DoseyTextField(
                        value = endDate,
                        onValueChange = { endDate = it },
                        placeholder = "DD/MM/YYYY"
                    )
                }
            }
        }

//        PILLS
        item {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    DoseyLabel("Total")
                    DoseyTextField(value = totalPills, onValueChange = { totalPills = it }, placeholder = "30")
                }
                Column(modifier = Modifier.weight(1f)) {
                    DoseyLabel("Left")
                    DoseyTextField(value = pillsLeft, onValueChange = { pillsLeft = it }, placeholder = "30")
                }
            }
        }


//        REMINDER TIME
        item {
            DoseyPickerField(
                label = "Reminder Time",
                value = time,
                placeholder = "Select time",
                onClick = { showTimePicker = true }
            )
        }
        // Save Button
        item {
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    if (medName.isNotEmpty() && time.isNotEmpty()) {
                        viewModel.addMedicine(
                            name = medName,
                            desc = desc,
                            dosage = dosage,
                            time = time,
                            frequency = frequency,
                            startDate = startDate,
                            endDate = endDate,
                            totalPills = totalPills.toIntOrNull() ?: 0,
                            pillsLeft = pillsLeft.toIntOrNull() ?: 0
                        ) { success, message ->
                            if (success) onBack()
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
    }
    if (showTimePicker) {
        Dialog(onDismissRequest = { showTimePicker = false }) {
            Surface(
                shape = RoundedCornerShape(32.dp),
                color = Color(0xFFF5F3FF),
                tonalElevation = 12.dp,
                modifier = Modifier.padding(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Set Reminder Time",
                        style = MaterialTheme.typography.labelLarge,
                        color = PrimaryPurple,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    TimePicker(state = timePickerState)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showTimePicker = false }) {
                            Text("Cancel")
                        }
                        TextButton(onClick = {
                            val cal = Calendar.getInstance()
                            cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                            cal.set(Calendar.MINUTE, timePickerState.minute)

                            val formatter = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
                            time = formatter.format(cal.time)

                            showTimePicker = false
                        }) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }
}