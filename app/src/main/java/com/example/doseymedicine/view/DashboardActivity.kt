package com.example.doseymedicine.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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

    Scaffold(
//        topBar = {
//            TopAppBar(
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = PrimaryPurple,
//                    titleContentColor = SoftPurple
//                ),
//                title = { Text("Dosey")},
//                navigationIcon = {
//                    IconButton(onClick = { }) {
//                        Icon(
//                            painter = painterResource(R.drawable.outline_arrow_back_24),
//                            contentDescription = "Back",
//                            tint = Color.White
//                        )
//                    }
//                },
//                actions = {
//                    Image(
//                        painter = painterResource(id = R.drawable.doseycharacterbgremoved),
//                        contentDescription = "Logo",
//                        modifier = Modifier
//                            .size(115.dp)
//                            .padding(end = 6.dp)
//                            .clip(CircleShape)
//                    )
//                }
//
//            )
//        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
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
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedIndex) {
                0 -> HomeScreen(viewModel)
                1 -> Box { Text("History Archive Coming Soon") }
                2 -> Box { Text("Profile Settings Coming Soon") }
            }
        }
    }
}