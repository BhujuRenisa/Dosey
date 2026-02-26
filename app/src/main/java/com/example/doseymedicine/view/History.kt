package com.example.doseymedicine.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.model.MedicineModel
import com.example.doseymedicine.viewmodel.MedicineViewModel

@Composable
fun History(viewModel: MedicineViewModel) {
    val medicines by viewModel.medicines.observeAsState(emptyList())

    val historyList = medicines.filter { it.taken }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf
                (Color(0xFFFDFCFB),
                Color(0xFFE2D1F9))))
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Medication History",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBrown,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top= 12.dp, bottom = 16.dp)
            )
        }

        if (historyList.isEmpty()) {
            item {
                Box(modifier = Modifier.fillParentMaxSize(),
                    contentAlignment = Alignment.Center) {
                    Text("No history found. Take your meds to see them here!",
                        color = Color.Gray)
                }
            }
        } else {
            items(historyList) { medicine ->
                HistoryCard(medicine)
            }
        }
    }
}

@Composable
fun HistoryCard(medicine: MedicineModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = medicine.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp)
                Text(text = "Taken at ${medicine.time}",
                    color = Color.Gray,
                    fontSize = 14.sp)
            }
        }
    }
}