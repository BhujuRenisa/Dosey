package com.example.doseymedicine.View

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.ui.theme.PrimaryPurple
import com.example.doseymedicine.ui.theme.DarkText

class Otp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OTPBody()
        }
    }
}

@Composable
fun OTPBody() {
    val context = LocalContext.current
    val activity = context as Activity

    var otpValues by remember { mutableStateOf(listOf("", "", "", "", "")) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFF3E5F5), Color(0xFFE8F5E9))
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Text(
                "Verify Email",
                style = TextStyle(
                    fontSize = 30.sp,
                    color = PrimaryPurple,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                "Check your email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                style = TextStyle(
                    fontSize = 22.sp,
                    color = DarkText,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                "We've sent a 5-digit OTP code to your email. Please enter it below to verify your identity.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 15.dp),
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    color = DarkText.copy(alpha = 0.7f),
                    lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // OTP INPUT BOXES
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(5) { index ->
                    Box(
                        modifier = Modifier
                            .size(55.dp)
                            .background(Color.White, RoundedCornerShape(12.dp))
                            .border(
                                width = 1.5.dp,
                                color = PrimaryPurple.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryPurple
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            // RESET PASSWORD
            Button(
                onClick = {
                    val intent = Intent(context, NewPassword::class.java)
                    context.startActivity(intent)
                    activity.finish()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 30.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFFBA68C8), PrimaryPurple)
                            )
                        )
                        .clip(RoundedCornerShape(30.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Verify & Proceed",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // RESEND SECTION
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Haven't got the email? ",
                    color = DarkText.copy(alpha = 0.6f)
                )

                Text(
                    text = "Resend email",
                    color = PrimaryPurple,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                    }
                )
            }
        }
    }
}