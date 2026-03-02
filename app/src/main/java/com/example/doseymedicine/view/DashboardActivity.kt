package com.example.doseymedicine.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doseymedicine.ui.theme.PrimaryPurple
import com.example.doseymedicine.ui.theme.SoftPurple
import com.example.doseymedicine.viewmodel.MedicineViewModel
import com.example.doseymedicine.R
import com.google.firebase.auth.FirebaseAuth


class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MedicineViewModel = viewModel()

            Dashboard(viewModel)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(viewModel: MedicineViewModel) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val labels = listOf("Home", "History", "Profile")
    val icons = listOf(Icons.Default.Home, Icons.Default.DateRange, Icons.Default.Person)

    var isAddingMedicine by remember { mutableStateOf(false) }
    var selectedMedicineId by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF3E5F5),
            Color(0xFFE8F5E9)

        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                if (!isAddingMedicine && selectedMedicineId == null) {
                    NavigationBar(
                        containerColor = Color.White.copy(alpha = 0.8f)
                    ) {
                        labels.forEachIndexed { index, label ->
                            NavigationBarItem(
                                selected = selectedIndex == index,
                                onClick = { selectedIndex = index },
                                label = { Text(label) },
                                icon = { Icon(icons[index], contentDescription = label) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = PrimaryPurple,
                                    indicatorColor = PrimaryPurple.copy(alpha = 0.1f)
                                )
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                // Inside your Dashboard function...
                when {
                    selectedMedicineId != null -> {
                        MedicineDetailsScreen(
                            viewModel = viewModel,
                            medicineId = selectedMedicineId!!,
                            onBack = { selectedMedicineId = null }
                        )
                    }

                    isAddingMedicine -> {
                        AddMedicine(
                            viewModel = viewModel,
                            onBack = { isAddingMedicine = false }
                        )
                    }

                    else -> {
                        when (selectedIndex) {
                            0 -> HomeScreen(
                                viewModel = viewModel,
                                onNavigateToAdd = { isAddingMedicine = true },
                                onNavigateToDetails = { id -> selectedMedicineId = id }
                            )
                            1 -> History(viewModel = viewModel)
                            2 -> Profile(
                                viewModel = viewModel,
                                onLogout = {
                                    FirebaseAuth.getInstance().signOut()

                                    val intent = Intent (
                                        context, LoginScreen::class.java).apply{
                                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    }
                                context.startActivity(intent)
                                    (context as? Activity)?.finish()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}