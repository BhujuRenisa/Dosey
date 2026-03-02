package com.example.doseymedicine

import FakeAuthRepo
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.example.doseymedicine.view.DoseyRegister
import com.example.doseymedicine.view.LoginScreen
import com.example.doseymedicine.view.RegisterBody
import com.example.doseymedicine.viewmodel.DoseyViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<androidx.activity.ComponentActivity>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun successfulRegistration_navigatesToLoginScreen() {
        val fakeViewModel = DoseyViewModel(FakeAuthRepo())

        composeRule.setContent {
            RegisterBody(viewModel = fakeViewModel)
        }

        composeRule.onNodeWithTag("firstName").performTextInput("Renisa")
        composeRule.onNodeWithTag("lastName").performTextInput("Bhuju")
        composeRule.onNodeWithTag("email").performTextInput("renisa@test.com")
        composeRule.onNodeWithTag("password").performTextInput("Password123")
        composeRule.onNodeWithTag("termsCheckbox").performClick()
        composeRule.onNodeWithTag("registerButton").performClick()

        composeRule.waitForIdle()

        intended(hasComponent(LoginScreen::class.java.name))
    }
}