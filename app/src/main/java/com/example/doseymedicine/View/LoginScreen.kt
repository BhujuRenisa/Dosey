package com.example.doseymedicine.View

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.Respo.AuthRepoImpl
import com.example.doseymedicine.R
import com.example.doseymedicine.viewmodel.DoseyViewModel
import com.example.doseymedicine.View.ForgotPassword
import com.example.doseymedicine.ui.theme.PrimaryPurple
import com.example.doseymedicine.ui.theme.SoftPurple
import com.example.doseymedicine.ui.theme.DarkText

class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
//        if (auth.currentUser != null) {
//            startActivity(Intent(this, DashboardActivity::class.java))
//            finish()
//            return
//        }
        enableEdgeToEdge()
        setContent {
            DoseyLoginBody()
        }
    }
}



@Composable
fun DoseyLoginBody() {

    val viewModel = remember { DoseyViewModel(AuthRepoImpl()) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as Activity

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFF3E5F5),
                            Color(0xFFE8F5E9)
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFFEDE7F6), Color(0xFFF0F4C3))
                        )
                    )
                    .border(1.dp, Color(0xFFD1C4E9), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.doseycharacter),
                    contentDescription = "Dosey Character",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Sign in to Dosey",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryPurple
            )

            Text(
                "Your daily medicine companion",
                fontSize = 16.sp,
                color = DarkText,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // EMAIL FIELD
            OutlinedTextField(
                value = email,
                onValueChange = { data -> email = data },
                placeholder = { Text("Enter your Email", color = Color(0xFF90A4AE)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = SoftPurple.copy(alpha = 0.5f),
                    focusedContainerColor = SoftPurple.copy(alpha = 0.7f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryPurple
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // PASSWORD FIELD
            OutlinedTextField(
                value = password,
                onValueChange = { data -> password = data },
                placeholder = { Text("********", color = Color(0xFF90A4AE)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = SoftPurple.copy(alpha = 0.5f),
                    focusedContainerColor = SoftPurple.copy(alpha = 0.7f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryPurple
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 24.dp),
                visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton (onClick = { visibility = !visibility }) {
                        Icon(
                            painter = painterResource(
                                id = if (visibility) R.drawable.outline_visibility_24 else R.drawable.outline_visibility_off_24
                            ),
                            contentDescription = if (visibility) "Hide password" else "Show password",
                            tint = PrimaryPurple
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Forgot Password
            Text(
                "Forgot Password?",
                modifier = Modifier
                    .padding(end = 24.dp)
                    .align(Alignment.End)
                    .clickable{
                        context.startActivity(Intent(context, ForgotPassword::class.java))
            },
                textAlign = TextAlign.End,
                color = PrimaryPurple,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline
            )

            Spacer(modifier = Modifier.height(40.dp))

            // LOGIN BUTTON
            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "All fields required", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    viewModel.login(email.trim(), password.trim()) { success, msg ->
                        if (success) {
                            context.startActivity(Intent(context, DashboardActivity::class.java))
                            activity.finish()
                        } else {
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFFBA68C8), Color(0xFF6A1B9A))
                            )
                        )
                        .clip(RoundedCornerShape(30.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Login",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // SIGN UP
            Row(
                modifier = Modifier
                    .padding(bottom = 32.dp) // Gives some space from the bottom edge
                    .clickable {
                        context.startActivity(Intent(context, DoseyRegister::class.java))
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = DarkText,
                    fontSize = 15.sp
                )
                Text(
                    text = "Sign Up",
                    color = PrimaryPurple,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}