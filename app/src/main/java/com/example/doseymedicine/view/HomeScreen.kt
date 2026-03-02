package com.example.doseymedicine.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.R
import com.example.doseymedicine.model.MedicineModel
import com.example.doseymedicine.ui.theme.DarkText
import com.example.doseymedicine.ui.theme.DoseyPurple
import com.example.doseymedicine.ui.theme.MutedText
import com.example.doseymedicine.ui.theme.Purple80
import com.example.doseymedicine.ui.theme.doseyText
import com.example.doseymedicine.viewmodel.MedicineViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

val SurfaceCardDark = Color(0xFF1E212D)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MedicineViewModel,
    onNavigateToAdd: () -> Unit ,
    onNavigateToDetails: (String) -> Unit)
{
    val userData by viewModel.userData
    val medicineList by viewModel.medicines.observeAsState(initial = emptyList())
    var selectedDate by remember { mutableStateOf(Calendar.getInstance().time) }

    val allMedicines by viewModel.medicines.observeAsState(emptyList())
    val upcomingMeds = allMedicines.filter { !it.taken }

    LaunchedEffect(Unit) {
        viewModel.loadMedicines()
        viewModel.fetchUserData()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF3E5F5),
                        Color(0xFFE8F5E9)
                    )
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    title = {
                        Text(
                            "My Medications",
                            color = DarkText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    },

                    actions = {
                        Box(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.9f))
                                .border(1.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.doseycharacterbgremoved),
                                contentDescription = "Dosey Mascot",
                                modifier = Modifier
                                    .size(42.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onNavigateToAdd,
                    containerColor = DoseyPurple,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        )
        { padding ->
            Column(modifier = Modifier.padding(padding).fillMaxSize()) {

                HeaderSection(
                    name = if (userData.firstName.isNotBlank()) userData.firstName else "User",
                    medicineList.count { !it.taken })

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
                        val filteredMeds = medicineList.filter {
                            getPeriod(it.time) == period && !it.taken
                        }

                        if (filteredMeds.isNotEmpty()) {
                            item { SectionHeader(period) }
                            items(filteredMeds) { med ->
                                MedCard(
                                    med = med,
                                    onCheck = { viewModel.markTaken(med.id) },
                                    onClick = {
                                        onNavigateToDetails(med.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderSection(name:String,
                  remaining: Int) {


    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    val greeting = when (hour) {
        in 4..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        else -> "Good Evening"
    }

    Column(modifier = Modifier.padding(horizontal = 24.dp,
        vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("$greeting, ",
                fontSize = 26.sp,
                color = doseyText,
                fontWeight = FontWeight.Light)

            Text("$name",
                fontSize = 26.sp,
                color = DoseyPurple,
                fontWeight = FontWeight.Bold)
        }
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Box(modifier = Modifier.size(6.dp)
                .background(DoseyPurple, CircleShape)
            )
            Text("  You have $remaining meds left today.",
                color = MutedText, fontSize = 18.sp)
        }
    }
}

@Composable
fun MedCard(med: MedicineModel, onCheck: () -> Unit,
            onClick: () -> Unit ) {
    val isTaken = med.taken

    Surface(
        color = Color.White,
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 0.dp,
        shadowElevation = 6.dp,

        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(DoseyPurple.copy(alpha = 0.1f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pill_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(34.dp)
                        .alpha(if (isTaken) 0.4f else 1f)
                )
            }

            Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                Text(
                    text = med.name,
                    color = if (isTaken) Color.Gray else doseyText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = med.desc,
                    color = MutedText,
                    fontSize = 13.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (isTaken) DoseyPurple else Color.Transparent
                    )
                    .border(
                        2.dp,
                        if (isTaken) DoseyPurple else Color.LightGray,
                        CircleShape
                    )
                    .clickable { onCheck() },
                contentAlignment = Alignment.Center
            ) {
                if (isTaken) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = MutedText,
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

        repeat(21) {
            list.add(cal.time)
            cal.add(Calendar.DAY_OF_YEAR, 1)
        }
        list
    }

    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val dateFormat = SimpleDateFormat("d", Locale.getDefault())


    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        if (!isSameDay(selectedDate, today)) {
            Text(
                text = "Go to Today",
                color = doseyText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        onDateSelected(today)
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }
                    .padding(bottom = 12.dp),
                textDecoration = TextDecoration.Underline
            )
        }

        LazyRow(
            state = listState,
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
                        color = if (isSelected) Color.White else MutedText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = dateFormat.format(date),
                        color = if (isSelected) Color.White else doseyText,
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