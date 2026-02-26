package com.example.doseymedicine.model

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class UserProfileModel(
    val bloodType: String = "",
    val allergies: String = "",
    val emergencyContact: String = ""
)