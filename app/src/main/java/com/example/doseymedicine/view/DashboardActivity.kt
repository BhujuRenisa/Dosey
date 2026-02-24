package com.example.doseymedicine.view

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doseymedicine.ui.theme.PrimaryPurple
import com.example.doseymedicine.ui.theme.SoftPurple
import com.example.doseymedicine.viewmodel.MedicineViewModel
import com.example.doseymedicine.R


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

    // Your preferred standard color scheme
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFC8E6F0), Color(0xFFDCDAF0))
    )

    var selectedMedicineId by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                // Only show bottom bar when not adding a medicine
                if (!isAddingMedicine) {
                    NavigationBar(
                        containerColor = Color.White.copy(alpha = 0.8f) // Slight transparency for a modern look
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
            // Use standard padding from the Scaffold
            Box(modifier = Modifier.padding(paddingValues)) {

                when {
                    selectedMedicineId != null -> {
                        selectedMedicineId?.let { id ->
                            MedicineDetailsScreen(
                                viewModel =viewModel,
                                medicineId = id,
                                onBack = { selectedMedicineId = null }
                            )
                        }
                    }

                    isAddingMedicine -> {
                        AddMedicineScreen(
                            viewModel = viewModel,
                            onBack = { isAddingMedicine = false }
                        )
                    }

                    else -> {
                        when (selectedIndex) {
                            0 -> HomeScreen(
                                viewModel = viewModel,
                                onNavigateToAdd = { isAddingMedicine = true },
                                onNavigateToDetails = { id ->
                                    selectedMedicineId = id
                                }
                            )
                            1 -> History()
                            2 -> Profile()
                        }
                    }
                }
            }
        }
    }
}