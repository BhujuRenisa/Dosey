package com.example.doseymedicine.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.model.MedicineModel
import com.example.doseymedicine.ui.theme.DarkText
import com.example.doseymedicine.ui.theme.DoseyPurple
import com.example.doseymedicine.ui.theme.MutedText
import com.example.doseymedicine.viewmodel.MedicineViewModel

@Composable

fun MedicineDetailsScreen(
    viewModel: MedicineViewModel,
    medicineId: String,
    onBack: () -> Unit
) {
    var medicine by remember { mutableStateOf<MedicineModel?>(null) }

    LaunchedEffect(medicineId) {
        viewModel.getMedicineById(medicineId) {
            medicine = it
        }
    }

    if (medicine == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val med = medicine!!

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Back Button
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, "Back")
            }

            Spacer(Modifier.height(12.dp))

            // Medicine Name
            Text(
                text = med.name.ifBlank { "Medicine Details" },
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText // Use the DarkText we defined for the light theme
            )

            Spacer(Modifier.height(16.dp))

// Description Card showing the new fields
            DetailItem(label = "Description", value = med.desc)
            DetailItem(label = "Reminder Time", value = med.time)
            DetailItem(label = "Frequency", value = med.frequency)
            DetailItem(label = "Start Date", value = med.startDate)
            DetailItem(label = "End Date", value = med.endDate)

            Spacer(Modifier.height(16.dp))

// Pills Progress Section
            Text(
                text = "Pill Consumption Status",
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(Modifier.height(8.dp))

            val progress = if (med.totalPills > 0) {
                med.pillsLeft.toFloat() / med.totalPills.toFloat()
            } else {
                0f
            }

            LinearProgressIndicator(
                progress = {progress},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(10.dp)),
                color = DoseyPurple,
                trackColor = DoseyPurple.copy(alpha = 0.2f)
            )

            Text(
                text = "${med.pillsLeft} of ${med.totalPills} pills remaining",
                fontSize = 12.sp,
                color = MutedText,
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(Modifier.height(16.dp))

            // Low stock warning ⭐
            if (med.pillsLeft <= 5) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFEBEE)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "⚠ Low medicine stock! Please refill.",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(text = label, color = Color.Gray, fontSize = 14.sp)
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Medium)
    }
}