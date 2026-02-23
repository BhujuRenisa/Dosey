package com.example.doseymedicine.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.R
import com.example.doseymedicine.model.MedicineModel
import com.example.doseymedicine.viewmodel.MedicineViewModel
import java.text.SimpleDateFormat
import java.util.*

val DeepNavyBackground = Color(0xFF12141C)
val SurfaceCardDark = Color(0xFF1E212D)
val DoseyPurple = Color(0xFF9883E5)
val PurplePinkAccent = Color(0xFFE583D2)
val TextMuted = Color(0xFF9496A1)
val White = Color(0xFFFFFFFF)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MedicineViewModel) {
    val medicineList by viewModel.medicines.observeAsState(initial = emptyList())
    var selectedDate by remember { mutableStateOf(Calendar.getInstance().time) }

    LaunchedEffect(Unit) {
        viewModel.loadMedicines()
    }

    Scaffold(
        containerColor = DeepNavyBackground,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text("My Medications", color = White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = White)
                    }
                },
                actions = {
                    Box(modifier = Modifier.padding(end = 16.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.doseycharacterbgremoved),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(SurfaceCardDark)
                        )
                        // Badge style Add button
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(DoseyPurple, CircleShape)
                                .align(Alignment.TopEnd)
                                .border(1.5.dp, DeepNavyBackground, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, null, modifier = Modifier.size(10.dp), tint = DeepNavyBackground)
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {

            HeaderSection(medicineList.count { !it.taken })

            FullFeatureCalendar(
                selectedDate = selectedDate,
                onDateSelected = { newDate -> selectedDate = newDate }
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val groups = listOf("MORNING", "AFTERNOON", "EVENING")

                groups.forEach { period ->
                    val filteredMeds = medicineList.filter { getPeriod(it.time) == period }

                    if (filteredMeds.isNotEmpty()) {
                        item { SectionHeader(period) }
                        items(filteredMeds) { med ->
                            MedCard(med) { viewModel.markTaken(med.id) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderSection(remaining: Int) {
    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Good Morning, ", fontSize = 26.sp, color = White, fontWeight = FontWeight.Light)
            Text("Alex.", fontSize = 26.sp, color = DoseyPurple, fontWeight = FontWeight.Bold)
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
            Box(modifier = Modifier.size(6.dp).background(DoseyPurple, CircleShape))
            Text("  You have $remaining meds left today.", color = TextMuted, fontSize = 14.sp)
        }
    }
}

@Composable
fun MedCard(med: MedicineModel, onCheck: () -> Unit) {
    val isTaken = med.taken

    Surface(
        color = Color(0xFF1E212D), // Deep Charcoal
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isTaken) Color.Transparent else DoseyPurple.copy(alpha = 0.2f)
        ),
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with a soft background glow
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(DoseyPurple.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.pill_image), // Use a high-quality pill SVG
                    contentDescription = null,
                    tint = if (isTaken) Color.Gray else DoseyPurple,
                    modifier = Modifier.size(28.dp)
                )
            }

            Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                Text(
                    text = med.name,
                    color = if (isTaken) Color.Gray else Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = med.desc,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            IconButton(
                onClick = onCheck,
                modifier = Modifier
                    .size(36.dp)
                    .background(if (isTaken) DoseyPurple else Color.Transparent, CircleShape)
                    .border(2.dp, if (isTaken) DoseyPurple else Color.Gray.copy(alpha = 0.5f), CircleShape)
            ) {
                if (isTaken) Icon(Icons.Default.Check, null, tint = Color.Black, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = TextMuted,
        fontSize = 11.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 1.5.sp,
        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
    )
}

@Composable
fun FullFeatureCalendar(
    selectedDate: Date,
    onDateSelected: (Date) -> Unit
) {
    val today = Calendar.getInstance().time
    val dateList = remember {
        val list = mutableListOf<Date>()
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -7)
        repeat(21) {
            list.add(cal.time)
            cal.add(Calendar.DAY_OF_YEAR, 1)
        }
        list
    }

    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val dateFormat = SimpleDateFormat("d", Locale.getDefault())

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        if (!isSameDay(selectedDate, today)) {
            Text(
                text = "Go to Today",
                color = PurplePinkAccent,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onDateSelected(today) }
                    .padding(bottom = 12.dp),
                textDecoration = TextDecoration.Underline
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(dateList) { date ->
                val isToday = isSameDay(date, today)
                val isSelected = isSameDay(date, selectedDate)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(52.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (isSelected) DoseyPurple else Color.Transparent)
                        .border(
                            width = if (isToday && !isSelected) 1.dp else 0.dp,
                            color = DoseyPurple.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(18.dp)
                        )
                        .clickable { onDateSelected(date) }
                        .padding(vertical = 12.dp)
                ) {
                    Text(
                        text = dayFormat.format(date).uppercase(),
                        color = if (isSelected) DeepNavyBackground else TextMuted,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = dateFormat.format(date),
                        color = if (isSelected) DeepNavyBackground else White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

fun getPeriod(time: String): String {
    return when {
        time.contains("AM", true) -> "MORNING"
        time.contains("PM", true) -> {
            val hour = time.split(":")[0].toIntOrNull() ?: 0
            if (hour in 1..5 || hour == 12) "AFTERNOON" else "EVENING"
        }
        else -> "MORNING"
    }
}

fun isSameDay(date1: Date, date2: Date): Boolean {
    val cal1 = Calendar.getInstance().apply { time = date1 }
    val cal2 = Calendar.getInstance().apply { time = date2 }
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}