package com.example.doseymedicine.View

import android.app.Activity
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// VVVV IMPORTANT IMPORTS: ADJUST THESE BASED ON YOUR PROJECT STRUCTURE VVVV
import com.example.doseymedicine.R
import com.example.doseymedicine.viewmodel.DoseyViewModel
import com.example.doseymedicine.Respo.AuthRepoImpl
import com.example.doseymedicine.ui.theme.PrimaryPurple
import com.example.doseymedicine.ui.theme.SoftPurple
import com.example.doseymedicine.ui.theme.DarkText
import com.example.doseymedicine.ui.theme.White
// VVVV END OF IMPORTS VVVV

class DoseyRegister : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegisterBody()
        }
    }
}

@Composable
fun RegisterBody(){

    val viewModel = remember { DoseyViewModel(AuthRepoImpl()) }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(false) }
    var checkbox by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as Activity

    // Note: Calendar/selectedDate variables are defined but not used in the final UI logic.

    Scaffold { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFF3E5F5), Color(0xFFE8F5E9))
                    )
                )
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier .height(40 .dp))

            // --- Icon / Header ---
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFFEDE7F6), Color(0xFFF0F4C3))
                        )
                    )
                    .border(1.dp, Color(0xFFD1C4E9), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_person_24),
                    contentDescription = "User Icon",
                    modifier = Modifier.size(80.dp),
                    tint = PrimaryPurple
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Create Your Account",
                modifier = Modifier.fillMaxWidth(),
                style= TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    color= PrimaryPurple,
                    fontWeight = FontWeight.Bold
                )
            )
            Text("Join our platform to browse products and track your orders.",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = DarkText.copy(0.7f),
                    fontSize = 14.sp
                ),
                modifier = Modifier .padding(vertical = 20.dp)
            )

            // --- INPUT FIELDS ---
            val fieldModifier = Modifier
                .fillMaxWidth()
                .height(56.dp)

            // First Name Field
            OutlinedTextField(
                value = firstName,
                onValueChange = { data -> firstName = data },
                placeholder = { Text("First Name", color = Color(0xFF90A4AE)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = SoftPurple.copy(alpha = 0.5f),
                    focusedContainerColor = SoftPurple.copy(alpha = 0.7f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryPurple
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = fieldModifier
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Last Name Field
            OutlinedTextField(
                value = lastName,
                onValueChange = { data -> lastName = data },
                placeholder = { Text("Last Name", color = Color(0xFF90A4AE)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = SoftPurple.copy(alpha = 0.5f),
                    focusedContainerColor = SoftPurple.copy(alpha = 0.7f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryPurple
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = fieldModifier
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { data -> email = data },
                placeholder = { Text("abc@gmail.com", color = Color(0xFF90A4AE)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = SoftPurple.copy(alpha = 0.5f),
                    focusedContainerColor = SoftPurple.copy(alpha = 0.7f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryPurple
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = fieldModifier
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { data -> password = data },
                placeholder = { Text("Password", color = Color(0xFF90A4AE)) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = SoftPurple.copy(alpha = 0.5f),
                    focusedContainerColor = SoftPurple.copy(alpha = 0.7f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryPurple
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = fieldModifier,
                visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton (onClick = { visibility = !visibility }) {
                        Icon(
                            painter = if (visibility)
                                painterResource(R.drawable.outline_visibility_off_24)
                            else painterResource(
                                R.drawable.outline_visibility_24
                            ),
                            contentDescription = null,
                            tint = PrimaryPurple
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Checkbox ---
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Checkbox(
                    checked = checkbox,
                    onCheckedChange = { checkbox = it },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor =White,
                        checkedColor = PrimaryPurple
                    )
                )
                Text(
                    "I agree to terms & Conditions",
                    color = DarkText,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { checkbox = !checkbox }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // REGISTER BUTTON
            Button (
                onClick = {
                    if (!checkbox) {
                        Toast.makeText(context,"Please accept terms & conditions",Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "All fields required", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    viewModel.register(
                        email = email.trim().lowercase(),
                        password = password.trim(),
                        firstName = firstName.trim(),
                        lastName = lastName.trim()
                    ) { success, message ->
                        if (success) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(context, LoginScreen::class.java)
                            context.startActivity(intent)
                            activity.finish()
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
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
                        "Register",
                        color = White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Link to Login Screen
            TextButton(onClick = {
                activity.finish()
            }) {
                Text(
                    text = "Already have an account? Sign In",
                    color = PrimaryPurple,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}