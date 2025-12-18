package com.example.doseymedicine.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.R
import com.example.doseymedicine.ui.theme.*

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardBody()
        }
    }
}

data class MedInfo(
    val name: String, val desc: String, val time: String,
    val icon: Int, val iconBg: Color, val iconTint: Color,
    val timeColor: Color = Color(0xFF4CAF50), val isTakeable: Boolean = true
)

fun getMedications() = listOf(
    MedInfo("Vitamin D3", "1000IU • Once daily", "12:00 PM", R.drawable.outline_sunny_24, Color(0xFFFFF9C4), Color(0xFFFFB300)),
    MedInfo("Lisinopril", "10mg • Before bed", "8:00 PM", R.drawable.outline_water_drop_24, Color(0xFFE3F2FD), Color(0xFF2196F3)),
    MedInfo("Metformin", "500mg • With dinner", "Tomorrow", R.drawable.outline_medication_24, Color(0xFFEEEEEE), Color(0xFF757575), Color.Gray, false)
)

@Composable
fun DashboardBody() {
    val context = LocalContext.current
    val mainGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFC8E6F0), Color(0xFFF0E0D0), Color(0xFFDCDAF0))
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(context, AddMedicine::class.java)
                    context.startActivity(intent)
                },
                containerColor = PrimaryPurple,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(65.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Medicine",
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(mainGradient)
                .padding(padding),
            contentPadding = PaddingValues(20.dp)
        ) {
            //  Top profile pic
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.doseycharacter),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Good Morning,", fontSize = 14.sp, color = DarkText.copy(0.6f))
                            Text("Sarah", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = DarkText)
                        }
                    }
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = DarkText.copy(0.5f))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    color = Color.White.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(50.dp),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_check_circle_24),
                            contentDescription = null,
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("95% Compliant", fontWeight = FontWeight.Bold, color = DarkText)
                        Text("  |  Last 7 days", color = DarkText.copy(0.5f), fontSize = 12.sp)
                    }
                }
            }

            //  Duenow
            item {
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(R.drawable.outline_medical_services_24), contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Due Now", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Surface(color = Color.Black.copy(0.1f), shape = RoundedCornerShape(12.dp)) {
                        Text("Critical", modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp), fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                // Critical Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A0A0A)),
                    shape = RoundedCornerShape(32.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Row {
                                Box(
                                    modifier = Modifier.size(50.dp).clip(CircleShape).background(Color(0xFF2C1616)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(painterResource(R.drawable.outline_medication_24), contentDescription = null, tint = Color(0xFFE57373))
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text("Amoxicillin", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    Text("500mg • Take with food", color = Color.Gray, fontSize = 14.sp)
                                }
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("8:00 AM", color = Color(0xFFE57373), fontWeight = FontWeight.Bold)
                                Text("Late by 15m", color = Color(0xFFE57373).copy(0.7f), fontSize = 12.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { /* Mark Taken */ },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Mark Taken", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // UPCOMING SECTION
            item {
                Spacer(modifier = Modifier.height(30.dp))
                Text("Upcoming", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(getMedications()) { med ->
                UpcomingMedCard(med)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun UpcomingMedCard(med: MedInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.7f)),
        shape = RoundedCornerShape(32.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(med.iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(med.icon),
                        contentDescription = null,
                        tint = med.iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = med.name,
                        fontWeight = FontWeight.Bold,
                        color = DarkText,
                        fontSize = 17.sp
                    )
                    Text(
                        text = med.desc,
                        color = DarkText.copy(alpha = 0.6f),
                        fontSize = 13.sp
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = med.time,
                    color = med.timeColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                if (med.isTakeable) {
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedButton(
                        onClick = { /* Handle Mark Taken */ },
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            width = 1.dp,
                            color = PrimaryPurple.copy(alpha = 0.4f)
                        )
                    ) {
                        Text(
                            text = "Mark Taken",
                            fontSize = 12.sp,
                            color = PrimaryPurple,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
