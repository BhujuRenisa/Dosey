package com.example.doseymedicine

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.doseymedicine.view.LoginScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.compose.ui.test.hasText

@RunWith(AndroidJUnit4::class)
class LoginInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<LoginScreen>()

    @Test
    fun loginButton_isDisplayed() {
        composeRule
            .onNodeWithTag("loginButton")
            .assertIsDisplayed()
    }

    @Test
    fun emailField_acceptsInput() {
        val email = "test@gmail.com"

        composeRule.onNodeWithTag("emailField")
            .performTextInput(email)

        composeRule.onNodeWithTag("emailField")
            .assertTextEquals(email)
    }

    @Test
    fun passwordField_acceptsInput() {
        val password = "Password123"

        composeRule.onNodeWithTag("passwordField")
            .performTextInput(password)

        composeRule.onNodeWithTag("passwordField")
            .assert(hasText(password))
    }

    @Test
    fun loginButton_clickable() {
        composeRule
            .onNodeWithTag("loginButton")
            .performClick()
    }
}