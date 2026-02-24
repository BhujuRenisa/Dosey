package com.example.doseymedicine.model

data class MedicineModel(
        val id: String = "",
        val name: String = "",
        val desc: String = "",
        val time: String = "",
        val frequency: String = "",
        val startDate: String = "",
        val endDate: String = "",
        val totalPills: Int = 0,
        val pillsLeft: Int = 0,
        val taken: Boolean = false
    )
