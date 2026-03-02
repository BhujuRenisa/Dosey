package com.example.doseymedicine.respo

import androidx.compose.remote.creation.profile.Profile
import com.example.doseymedicine.model.MedicineModel
import com.example.doseymedicine.model.UserProfileModel

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

    fun deleteMedicine(
        medicineId: String,
        callback: (Boolean) -> Unit
    )

    fun saveUserProfile(
        userId: String,
        profile: UserProfileModel,
        callback: (Boolean) -> Unit
    )

    fun getUserProfile(
        userId: String,
        callback: (UserProfileModel?) -> Unit
    )

    fun getUserData(
        userId: String,
        callback: (com.example.doseymedicine.model.DoseyModel?) -> Unit
    )

    fun updateUserData(
        userId: String,
        model: com.example.doseymedicine.model.DoseyModel,
        callback: (Boolean) -> Unit
    )
}