package com.example.doseymedicine.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.doseymedicine.R

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.ui.theme.DarkText
import com.example.doseymedicine.ui.theme.PrimaryPurple
import com.example.doseymedicine.viewmodel.MedicineViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Profile(
    viewModel: MedicineViewModel,
    onLogout: () -> Unit
) {

    val medicines by viewModel.medicines.observeAsState(emptyList())
    val activeMeds = medicines.size
    val adherence = 92 // you can calculate later

    val currentUser = FirebaseAuth.getInstance().currentUser
    val userEmail = currentUser?.email ?: "No Email"
    val userName = currentUser?.displayName ?: "User"

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF3E5F5),
            Color(0xFFE8F5E9)
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item { Spacer(modifier = Modifier.height(40.dp)) }

        // ⭐ Avatar
        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(3.dp, PrimaryPurple, CircleShape)
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = PrimaryPurple,
                    modifier = Modifier.size(55.dp)
                )
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        // ⭐ Name
        item {
            Text(
                text = userName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // ⭐ Email
        item {
            Text(
                text = userEmail,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }

        item { Spacer(modifier = Modifier.height(28.dp)) }

        // ⭐ Stats Card
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileStat(activeMeds.toString(), "Active Meds")
                    ProfileStat("$adherence%", "Adherence")
                }
            }
        }

        item { Spacer(modifier = Modifier.height(28.dp)) }

        // ⭐ Settings Section
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {

                    ProfileItem(Icons.Default.Settings, "Account Settings")
                    Divider()

                    ProfileItem(Icons.Default.Notifications, "Notification Tones")
                    Divider()

                    ProfileItem(Icons.Default.Info, "Privacy Policy")
                }
            }
        }

        item { Spacer(modifier = Modifier.height(40.dp)) }

        // ⭐ Logout Button
        item {
            OutlinedButton(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    onLogout()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Red
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_logout_24),
                    contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout", fontWeight = FontWeight.Bold)
            }
        }

        item { Spacer(modifier = Modifier.height(40.dp)) }
    }
}

@Composable
fun ProfileStat(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryPurple
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ProfileItem(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            icon,
            contentDescription = null,
            tint = PrimaryPurple
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp
        )
    }
}