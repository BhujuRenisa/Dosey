package com.example.doseymedicine.view

import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.ui.theme.PrimaryPurple
import com.example.doseymedicine.R


@Composable
fun TimeStep(
    startDate: String,
    reminderTime: String,
    onDateSelected: (String) -> Unit,
    onTimeSelected: (String) -> Unit
) {

    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
//            .padding(20.dp)
    ) {

        Text(
            "When would you like to start?",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3E2723)
        )

        Spacer(modifier = Modifier.height(24.dp))

        //Date Card
        ModernSelectionCard(
            title = "Start Date",
            value = if (startDate.isEmpty()) "Select Date" else startDate,
            icon = R.drawable.outline_date_range_24,
            onClick = { showDatePicker = true }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Time Card
        ModernSelectionCard(
            title = "Reminder Time",
            value = if (reminderTime.isEmpty()) "08:00 AM" else reminderTime,
            icon = R.drawable.outline_circle_notifications_24,
            onClick = { showTimePicker = true }
        )
    }

    if (showDatePicker) {
        DoseyDatePickerDialog(
            onDateSelected = {
                onDateSelected(it)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    if (showTimePicker) {
        DoseyTimePickerDialog(
            initialTime = reminderTime,
            onTimeSelected = {
                onTimeSelected(it)
                showTimePicker = false
            },
            onDismiss = { showTimePicker = false }
        )
    }
}

@Composable
fun ModernSelectionCard(
    title: String,
    value: String,
    icon: Int,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Background
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color(0xFFBA68C8).copy(alpha = 0.1f),
                        RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = Color(0xFFBA68C8),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    title,
                    fontSize = 13.sp,
                    color = Color(0xFF3E2723).copy(alpha = 0.5f),
                    fontWeight = FontWeight.Normal
                )
                Text(
                    value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3E2723)
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.outline_add_24),
                contentDescription = null,
                tint = DarkBrown.copy(alpha = 0.2f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoseyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog (
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val selectedDate = datePickerState.selectedDateMillis?.let {
                    val date = java.util.Date(it)
                    val format = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
                    format.format(date)
                } ?: "Today"
                onDateSelected(selectedDate)
            }) { Text("OK", color = PrimaryPurple) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoseyTimePickerDialog(
    initialTime: String,
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val state = rememberTimePickerState()

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val formattedTime = "${state.hour}:${state.minute.toString().padStart(2, '0')}"
                onTimeSelected(formattedTime)
            }) { Text("Confirm", color = PrimaryPurple) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(28.dp),
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Set Reminder Time", color = PrimaryPurple, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(20.dp))
                TimePicker(state = state)
            }
        }
    )
}