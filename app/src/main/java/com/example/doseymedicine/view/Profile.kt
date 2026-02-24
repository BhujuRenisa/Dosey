package com.example.doseymedicine.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
    // 1. Observe the actual medicine list from your ViewModel
    val medicines by viewModel.medicines.observeAsState(emptyList())
    val currentUser = FirebaseAuth.getInstance().currentUser

    // Standard Dosey Gradient
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFC8E6F0), Color(0xFFDCDAF0))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // --- IDENTITY SECTION ---
        Surface(
            modifier = Modifier.size(100.dp),
            shape = RoundedCornerShape(30.dp), // Modern professional rounded corners
            color = Color.White.copy(alpha = 0.6f)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.padding(20.dp),
                tint = PrimaryPurple
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = currentUser?.displayName ?: "User Name",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )
        Text(
            text = currentUser?.email ?: "user@example.com",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- STATS SECTION ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Reusing your logic to show total meds
            StatCard("Active Meds", medicines.size.toString(), Modifier.weight(1f))
            StatCard("Adherence", "92%", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- ACTIONS SECTION ---
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ProfileMenuItem(Icons.Default.Settings, "Account Settings")
            ProfileMenuItem(Icons.Default.Notifications, "Notification Tones")
            ProfileMenuItem(Icons.Default.Info, "Privacy Policy")

            Spacer(modifier = Modifier.height(24.dp))

            // Professional Logout Button
            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFEBEE), // Soft red tint
                    contentColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp) // Consistent with DoseyTextField
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Logout", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp), // Smooth corners for professional look
        color = Color.White.copy(alpha = 0.7f)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = PrimaryPurple)
            Text(label, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, title: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = 0.4f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = PrimaryPurple, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(16.dp))
            Text(title, fontWeight = FontWeight.Medium, color = DarkText)
            Spacer(Modifier.weight(1f))
//            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
        }
    }
}