package com.example.doseymedicine

import com.example.doseymedicine.viewmodel.DoseyViewModel
import com.example.doseymedicine.respo.AuthRepo
import org.junit.Test
import org.junit.Assert.*
import org.mockito.kotlin.*

class DoseyViewModelTest {

    @Test
    fun login_success_test() {

        val repo = mock<AuthRepo>()
        val viewModel = DoseyViewModel(repo)

        doAnswer {
            val callback = it.getArgument<(Boolean, String) -> Unit>(2)
            callback(true, "Login success")
            null
        }.`when`(repo).login(eq("test@gmail.com"),
            eq("123456"), any())

        var successResult = false
        var messageResult = ""

        viewModel.login("test@gmail.com", "123456") { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Login success", messageResult)

        verify(repo).login(eq("test@gmail.com"), eq("123456"), any())
    }


    @Test
    fun register_success_test() {

        val repo = mock<AuthRepo>()
        val viewModel = DoseyViewModel(repo)

        // Mock register
        doAnswer {
            val callback = it.getArgument<(Boolean, String, String) -> Unit>(2)
            callback(true, "Auth success", "user123")
            null
        }.`when`(repo).register(eq("test@gmail.com"), eq("123456"), any())

        // Mock addUserToDatabase
        doAnswer {
            val callback = it.getArgument<(Boolean, String) -> Unit>(2)
            callback(true, "Saved")
            null
        }.`when`(repo).addUserToDatabase(eq("user123"), any(), any())

        var successResult = false
        var messageResult = ""

        viewModel.register(
            "test@gmail.com",
            "123456",
            "Renisa",
            "Bhuju"
        ) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Registration successful!", messageResult)

        verify(repo).register(eq("test@gmail.com"), eq("123456"), any())
        verify(repo).addUserToDatabase(eq("user123"), any(), any())
    }

    @Test
    fun login_failure_test() {

        val repo = mock<AuthRepo>()
        val viewModel = DoseyViewModel(repo)

        doAnswer {
            val callback = it.getArgument<(Boolean, String) -> Unit>(2)
            callback(false, "Invalid credentials")
            null
        }.`when`(repo).login(eq("wrong@gmail.com"), eq("wrongpass"), any())

        var successResult = true
        var messageResult = ""

        viewModel.login("wrong@gmail.com", "wrongpass") { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertFalse(successResult)
        assertEquals("Invalid credentials", messageResult)

        verify(repo).login(eq("wrong@gmail.com"), eq("wrongpass"), any())
    }

    @Test
    fun register_auth_failure_test() {

        val repo = mock<AuthRepo>()
        val viewModel = DoseyViewModel(repo)

        doAnswer {
            val callback = it.getArgument<(Boolean, String, String) -> Unit>(2)
            callback(false, "Email already exists", "")
            null
        }.`when`(repo).register(eq("test@gmail.com"), eq("123456"), any())

        var successResult = true
        var messageResult = ""

        viewModel.register(
            "test@gmail.com",
            "123456",
            "Renisa",
            "Bhuju"
        ) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertFalse(successResult)
        assertEquals("Email already exists", messageResult)
    }

    @Test
    fun forgot_password_success_test() {

        val repo = mock<AuthRepo>()
        val viewModel = DoseyViewModel(repo)

        doAnswer {
            val callback = it.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Reset link sent")
            null
        }.`when`(repo).forgotPassword(eq("test@gmail.com"), any())

        var successResult = false
        var messageResult = ""

        viewModel.forgotPassword("test@gmail.com") { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Reset link sent", messageResult)

        verify(repo).forgotPassword(eq("test@gmail.com"), any())
    }
}