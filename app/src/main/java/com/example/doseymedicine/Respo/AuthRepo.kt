package com.example.doseymedicine.Respo

import com.example.doseymedicine.Model.DoseyModel

interface AuthRepo {

    fun login(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    )

    fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    )

    fun addUserToDatabase(
        userId: String,
        model: DoseyModel,
        callback: (Boolean, String) -> Unit
    )

    fun forgotPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    )

    fun deleteAccount(
        userId: String,
        callback: (Boolean, String) -> Unit
    )

    fun editProfile(
        userId: String,
        model: DoseyModel,
        callback: (Boolean, String) -> Unit
    )

    fun getUserById(
        userId: String,
        callback: (Boolean, String, DoseyModel?) -> Unit
    )
}
