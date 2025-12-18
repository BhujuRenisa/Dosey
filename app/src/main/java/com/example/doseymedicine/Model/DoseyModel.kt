package com.example.doseymedicine.Model

data class DoseyModel(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val gender: String = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "gender" to gender
        )
    }
}
