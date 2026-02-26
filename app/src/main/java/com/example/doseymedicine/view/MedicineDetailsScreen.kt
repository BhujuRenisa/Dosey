package com.example.doseymedicine.view

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.model.MedicineModel
import com.example.doseymedicine.viewmodel.MedicineViewModel
import java.util.Calendar


@Composable
fun MedicineDetailsScreen(
    viewModel: MedicineViewModel,
    medicineId: String,
    onBack: () -> Unit
) {
    var medicine by remember { mutableStateOf<MedicineModel?>(null) }
    var editMed by remember { mutableStateOf<MedicineModel?>(null) }

    // UI States
    var isEditing by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    // Dialog Temp States
    var fieldToEdit by remember { mutableStateOf("") }
    var tempTextFieldValue by remember { mutableStateOf("") }

    LaunchedEffect(medicineId) {
        viewModel.getMedicineById(medicineId) {
            medicine = it
            editMed = it
        }
    }

    if (medicine == null || editMed == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = SoftLavender)
        }
        return
    }

    Card(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp).verticalScroll(rememberScrollState())) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = DarkBrown)
                }
                Row {
                    if (isEditing) {
                        // Cancel Button
                        IconButton(onClick = {
                            isEditing = false
                            editMed = medicine
                        }) {
                            Icon(Icons.Default.Close, "Cancel", tint = DarkBrown.copy(alpha = 0.5f))
                        }
                    }

                    // Edit/Save Button
                    IconButton(onClick = {
                        if (isEditing) {
                            viewModel.updateMedicine(medicineId, editMed!!) { success ->
                                if (success) {
                                    medicine = editMed
                                    isEditing = false
                                }
                            }
                        } else {
                            isEditing = true
                        }
                    }) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = "Action",
                            tint = SoftLavender
                        )
                    }

                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, "Delete", tint = Color(0xFFE57373))
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = editMed!!.name.ifBlank { "Medicine Details" },
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = DarkBrown
            )

            Spacer(Modifier.height(24.dp))

            // Fields Section
            if (isEditing) {
                EditableDetailItem(
                    label = "Description",
                    value = editMed!!.desc,
                    isEditing = true,
                    onClick = {
                        fieldToEdit = "Description"
                        tempTextFieldValue = editMed!!.desc
                        showEditDialog = true
                    }
                )

                EditableDetailItem(
                    label = "Reminder Time",
                    value = editMed!!.time,
                    isEditing = true,
                    onClick = { showTimePicker = true }
                )

                EditableDetailItem(
                    label = "Frequency",
                    value = editMed!!.frequency,
                    isEditing = true,
                    onClick = {
                        fieldToEdit = "Frequency"
                        tempTextFieldValue = editMed!!.frequency
                        showEditDialog = true
                    }
                )

                EditableDetailItem(
                    label = "Dosage",
                    value = editMed!!.dosage,
                    isEditing = true,
                    onClick = {
                        fieldToEdit = "Dosage"
                        tempTextFieldValue = editMed!!.dosage
                        showEditDialog = true
                    }
                )
            } else {
                DetailItem(label = "Description", value = editMed!!.desc)
                DetailItem(label = "Reminder Time", value = editMed!!.time)
                DetailItem(label = "Frequency", value = editMed!!.frequency)
                DetailItem(label = "Start Date", value = editMed!!.startDate)
                DetailItem(label = "Dosage", value = editMed!!.dosage)
            }

            Spacer(Modifier.height(24.dp))

            // Progress Section
            Text(
                text = "Pill Consumption Status",
                fontWeight = FontWeight.Bold,
                color = DarkBrown.copy(alpha = 0.6f),
                fontSize = 14.sp
            )

            Spacer(Modifier.height(12.dp))
            val progress = if (editMed!!.totalPills > 0) editMed!!.pillsLeft.toFloat() / editMed!!.totalPills.toFloat() else 0f
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(12.dp).clip(CircleShape),
                color = SoftLavender,
                trackColor = SoftLavender.copy(alpha = 0.2f)
            )

            Text(
                text = "${editMed!!.pillsLeft} of ${editMed!!.totalPills} pills remaining",
                fontSize = 13.sp,
                color = DarkBrown.copy(alpha = 0.5f),
                modifier = Modifier.padding(top = 8.dp)
            )

            // Alerts
            if (editMed!!.pillsLeft <= 5) {
                val alpha by rememberInfiniteTransition(label = "").animateFloat(
                    initialValue = 0.4f, targetValue = 1f,
                    animationSpec = infiniteRepeatable(tween(800), RepeatMode.Reverse), label = ""
                )
                Text(
                    text = "⚠ Low Medicine Stock! Refill Soon",
                    color = Color.Red.copy(alpha = alpha),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }


    // Text Edit Dialog
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(24.dp),
            title = { Text("Edit $fieldToEdit", color = DarkBrown) },
            text = {
                OutlinedTextField(
                    value = tempTextFieldValue,
                    onValueChange = { tempTextFieldValue = it },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    editMed = when (fieldToEdit) {
                        "Description" -> editMed?.copy(desc = tempTextFieldValue)
                        "Frequency" -> editMed?.copy(frequency = tempTextFieldValue)
                        "Dosage" -> editMed?.copy(dosage = tempTextFieldValue)
                        else -> editMed
                    }
                    showEditDialog = false
                }) { Text("Done", color = SoftLavender) }
            }
        )
    }

    // Time Picker
    if (showTimePicker) {
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        android.app.TimePickerDialog(
            context, { _, h, m ->
                editMed = editMed?.copy(time = String.format("%02d:%02d", h, m))
                showTimePicker = false
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
        ).show()
    }

    // Delete Confirmation
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Medicine?") },
            text = { Text("Are you sure you want to remove ${medicine!!.name}?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteMedicine(medicineId) { if (it) onBack() }
                }) { Text("Delete", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(text = label,
            color = DarkBrown.copy(alpha = 0.4f),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold)
        Text(text = value.ifBlank { "Not set" },
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBrown)
    }
}

@Composable
fun EditableDetailItem(label: String, value: String, isEditing: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isEditing) SoftLavender.copy(alpha = 0.1f) else Color.Transparent)
            .clickable(enabled = isEditing) { onClick() }
            .padding(if (isEditing) 12.dp else 0.dp)
    ) {
        Text(text = label.uppercase(),
            color = DarkBrown.copy(alpha = 0.4f),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold)
        Text(text = value.ifBlank { "Not set" },
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBrown)
    }
}