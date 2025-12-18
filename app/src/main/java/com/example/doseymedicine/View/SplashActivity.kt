package com.example.doseymedicine.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.R
import com.example.doseymedicine.ui.theme.PrimaryPurple
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DoseySplashScreen()
        }
    }
}

@Composable
fun DoseySplashScreen() {
    val context = LocalContext.current
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000)
        context.startActivity(Intent(context, LoginScreen::class.java))
        (context as ComponentActivity).finish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFF3E5F5), // Soft Purple
                        Color(0xFFE8F5E9)  // Soft Green
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = startAnimation,
                enter = fadeIn(animationSpec = tween(1000)) +
                        scaleIn(animationSpec = tween(1000))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.doseycharacter),
                    contentDescription = "Dosey Character",
                    modifier = Modifier.size(180.dp)
                        .clip(CircleShape)

                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "DoseyMedicine",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PrimaryPurple,
                letterSpacing = 2.sp
            )

            Text(
                text = "Your daily medicine companion",
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            text = "v1.0",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
            color = Color.Gray.copy(alpha = 0.5f),
            fontSize = 12.sp
        )
    }
}