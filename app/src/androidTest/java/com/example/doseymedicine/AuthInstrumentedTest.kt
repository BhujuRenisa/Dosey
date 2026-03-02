package com.example.doseymedicine

import FakeAuthRepo
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthInstrumentedTest {

    private val repo = FakeAuthRepo()

    @Test
    fun forgotPasswordInstrumentedTest() {

        repo.forgotPassword("test@email.com") { success, message ->

            assertTrue(success)

            println(message)
        }
    }
}