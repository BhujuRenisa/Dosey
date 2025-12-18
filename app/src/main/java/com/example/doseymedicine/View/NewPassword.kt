package com.example.doseymedicine.View

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doseymedicine.R
import com.example.doseymedicine.ui.theme.PrimaryPurple
import com.example.doseymedicine.ui.theme.DarkText
import com.example.doseymedicine.ui.theme.SoftPurple

class NewPassword : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewPassBody()
        }
    }
}

@Composable
fun NewPassBody() {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
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
                        listOf(Color(0xFFF3E5F5), Color(0xFFE8F5E9))
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // --- HEADER ---
            Text(
                "Create New Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    fontSize = 28.sp,
                    color = PrimaryPurple,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Your new password must be different from previously used passwords to keep your medical data secure.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    fontSize = 15.sp,
                    color = DarkText.copy(alpha = 0.7f),
                    lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            // PASSWORD INPUT
            InputFieldLabel("New Password")
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("********", color = Color.Gray) },
                colors = doseyTextFieldColors(),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = { VisibilityIcon(visibility) { visibility = !visibility } }
            )

            Spacer(modifier = Modifier.height(25.dp))

            // CONFIRM PASSWORD INPUT
            InputFieldLabel("Confirm Password")
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("********", color = Color.Gray) },
                colors = doseyTextFieldColors(),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = { VisibilityIcon(visibility) { visibility = !visibility } }
            )

            Spacer(modifier = Modifier.height(50.dp))

            // UPDATE BUTTON
            Button(
                onClick = {
                    if (password.isEmpty() || confirmPassword.isEmpty()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else if (password != confirmPassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    } else {
                        // Logic to update password in ViewModel/Repo
                        Toast.makeText(context, "Password Updated Successfully", Toast.LENGTH_SHORT).show()
                        activity.finish() // Go back to login
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 24.dp),
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
                        "Update Password",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Composable
fun InputFieldLabel(label: String) {
    Text(
        text = label,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 8.dp),
        style = TextStyle(
            fontSize = 14.sp,
            color = DarkText,
            fontWeight = FontWeight.SemiBold
        )
    )
}

@Composable
fun doseyTextFieldColors() = TextFieldDefaults.colors(
    unfocusedContainerColor = Color.White,
    focusedContainerColor = Color.White,
    focusedIndicatorColor = PrimaryPurple,
    unfocusedIndicatorColor = Color.Transparent,
    cursorColor = PrimaryPurple,
    focusedTextColor = DarkText,
    unfocusedTextColor = DarkText
)

@Composable
fun VisibilityIcon(isVisible: Boolean, onToggle: () -> Unit) {
    IconButton(onClick = onToggle) {
        Icon(
            painter = painterResource(
                id = if (isVisible) R.drawable.outline_visibility_24
                else R.drawable.outline_visibility_off_24
            ),
            contentDescription = null,
            tint = PrimaryPurple
        )
    }
}