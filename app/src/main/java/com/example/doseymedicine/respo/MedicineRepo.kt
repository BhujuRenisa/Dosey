package com.example.doseymedicine.respo

import com.example.doseymedicine.model.MedicineModel

interface MedicineRepo {
    fun addMedicine(
        medicine: MedicineModel,
        callback: (Boolean, String) -> Unit
    )

    fun getMedicines(
        callback: (List<MedicineModel>) -> Unit
    )

    fun markTaken(
        medicineId: String,
        callback: (Boolean) -> Unit
    )

    fun getMedicineById(
        medicineId: String,
        callback: (MedicineModel?) -> Unit
    )

//    Edit medicine feature
fun updateMedicine(
    medicineId: String,
    updatedMed: MedicineModel,
    callback: (Boolean) -> Unit
)
}