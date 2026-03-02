package com.example.doseymedicine.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.model.UserProfileModel
import com.example.doseymedicine.ui.theme.PrimaryPurple
import com.example.doseymedicine.viewmodel.MedicineViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun Profile(
    viewModel: MedicineViewModel,
    onLogout: () -> Unit
) {
    val userProfile by viewModel.userProfile
    val userData by viewModel.userData
    val medicines by viewModel.medicines.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile()
        viewModel.fetchUserData()
    }

    // Edit Dialog
    var showEditDialog by remember { mutableStateOf(false) }
    var tempBlood by remember { mutableStateOf("") }
    var tempAllergies by remember { mutableStateOf("") }
    var tempEmergency by remember { mutableStateOf("") }
    var tempFirstName by remember { mutableStateOf("") }
    var tempLastName by remember { mutableStateOf("") }

    val userEmail = userData.email.ifBlank { "No Email" }
    val userName = if (userData.firstName.isNotBlank() || userData.lastName.isNotBlank()) {
        "${userData.firstName} ${userData.lastName}".trim()
    } else {
        "Health User"
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(
                Color(0xFFFDFCFB),
                Color(0xFFE2D1F9)
            ))
            )
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Spacer(modifier = Modifier.height(50.dp)) }

        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(2.dp, PrimaryPurple, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person,
                        contentDescription = null,
                        tint = PrimaryPurple,
                        modifier = Modifier.size(50.dp))
                }
                Spacer(Modifier.height(12.dp))
                Text(text = userName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkBrown)

                Text(text = userEmail,
                    fontSize = 14.sp,
                    color = Color.Gray)
            }
        }

        item { Spacer(Modifier.height(30.dp)) }

        item {
            Text("My Current Medications",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold, color = DarkBrown)

            Spacer(Modifier.height(12.dp))

            if (medicines.isEmpty()) {
                Text("No medicines added yet.",
                    color = Color.Gray,
                    fontSize = 13.sp)
            } else {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(medicines.size) { index ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = PrimaryPurple.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = medicines[index].name,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                color = PrimaryPurple,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(30.dp)) }

        item {
            Text("Emergency Health Info",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                color = DarkBrown)

            Spacer(Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(20.dp)) {
                    HealthRow(Icons.Default.Favorite,
                        "Blood Type",
                        userProfile.bloodType.ifBlank { "Not Set" }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.Gray.copy(alpha = 0.1f))

                    HealthRow(Icons.Default.Warning,
                        "Allergies",
                        userProfile.allergies.ifBlank { "None" }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.Gray.copy(alpha = 0.1f)
                    )

                    HealthRow(Icons.Default.Phone, "Emergency",
                        userProfile.emergencyContact.ifBlank { "Not Set" }
                    )

                    TextButton(
                        onClick = {
                            tempFirstName = userData.firstName
                            tempLastName = userData.lastName
                            tempBlood = userProfile.bloodType
                            tempAllergies = userProfile.allergies
                            tempEmergency = if (userProfile.emergencyContact.all { it.isDigit() }) {
                                userProfile.emergencyContact
                            } else {
                                ""
                            }
                            showEditDialog = true
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Update Info", color = PrimaryPurple)
                    }
                }
            }
        }

        item { Spacer(Modifier.height(30.dp)) }

        // Logout Button
        item {
            val context = LocalContext.current
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    Toast.makeText(context,"LogedOut Successfully", Toast.LENGTH_SHORT).show()
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE),
                    contentColor = Color.Red)
            ) {
                Text("Logout Account",
                    fontWeight = FontWeight.Bold)
            }
        }
        item { Spacer(Modifier.height(40.dp)) }
    }

    // Update Dialog
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(28.dp),
            title = { Text("Update Health Profile",
                fontWeight = FontWeight.Bold,
                color = DarkBrown)
                    },

            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = tempFirstName,
                        onValueChange = { tempFirstName = it },
                        label = { Text("First Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = tempLastName,
                        onValueChange = { tempLastName = it },
                        label = { Text("Last Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = tempBlood,
                        onValueChange = { tempBlood = it },
                        label = { Text("Blood Type") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = tempAllergies,
                        onValueChange = { tempAllergies = it },
                        label = { Text("Allergies") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = tempEmergency,
                        onValueChange = { input ->

                            if (input.isEmpty() || (input.all { it.isDigit() } && input.length <= 10)) {
                                tempEmergency = input
                            }
                        },
                        label = { Text("Emergency Contact (10 digits)") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = tempEmergency.length != 10 && tempEmergency.isNotEmpty(),
                        supportingText = {
                            if (tempEmergency.length != 10 && tempEmergency.isNotEmpty()) {
                                Text("Must be exactly 10 digits", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = tempEmergency.length == 10 && tempFirstName.isNotBlank(),
                    onClick = {
                    val updatedProfile = UserProfileModel(tempBlood, tempAllergies, tempEmergency)
                    viewModel.updateUserProfile(updatedProfile)
                    viewModel.updateUserData(tempFirstName, tempLastName) { }
                    showEditDialog = false
                }) {
                    Text("Save Changes",
                        color = if (tempEmergency.length == 10 && tempFirstName.isNotBlank()) PrimaryPurple else Color.Gray,
                        fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel",
                        color = Color.Gray)
                }
            }
        )
    }
}

@Composable
fun HealthRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(icon,
            contentDescription = null,
            tint = PrimaryPurple,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(12.dp))
        Text(text = label,
            modifier = Modifier.weight(1f),
            color = Color.Gray,
            fontSize = 14.sp)

        Text(text = value,
            fontWeight = FontWeight.Bold,
            color = DarkBrown)
    }
}