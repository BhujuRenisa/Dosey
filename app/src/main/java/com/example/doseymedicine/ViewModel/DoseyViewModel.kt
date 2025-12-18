package com.example.doseymedicine.viewmodel

import androidx.lifecycle.ViewModel
import com.example.doseymedicine.Model.DoseyModel
import com.example.doseymedicine.Respo.AuthRepo
import com.example.doseymedicine.Respo.AuthRepoImpl
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class DoseyViewModel(authRepoImpl: AuthRepoImpl) : ViewModel() {

    private val repo: AuthRepo = AuthRepoImpl()

    private val _userData = mutableStateOf<DoseyModel?>(null)
    val userData: State<DoseyModel?> = _userData

    fun fetchUser(userId: String) {
        repo.getUserById(userId) { success, message, model ->
            if (success) {
                _userData.value = model
            }
        }
    }


    fun login(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.login(email, password, callback)
    }

    fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.register(email, password) { success, msg, userId ->
            if (success) {

                val model = DoseyModel(
                    id = userId,
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    gender = ""
                )

                repo.addUserToDatabase(userId, model) { dbSuccess, dbMsg ->
                    if (dbSuccess) {
                        callback(true, "Registration successful!")
                    } else {
                        callback(false, dbMsg)
                    }
                }
            } else {
                callback(false, msg)
            }
        }
    }

    fun forgotPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.forgotPassword(email, callback)
    }
}
