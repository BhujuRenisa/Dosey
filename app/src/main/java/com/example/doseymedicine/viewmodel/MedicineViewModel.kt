package com.example.doseymedicine.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doseymedicine.model.MedicineModel
import com.example.doseymedicine.model.UserProfileModel
import com.example.doseymedicine.respo.MedicineRepo
import com.example.doseymedicine.respo.MedicineRepoImpl
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.State

class MedicineViewModel : ViewModel() {
    private val repo: MedicineRepo = MedicineRepoImpl()
    private val _medicines = MutableLiveData<List<MedicineModel>>()
    val medicines: LiveData<List<MedicineModel>> = _medicines
    fun loadMedicines() {
        repo.getMedicines {
            _medicines.postValue(it)
        }
    }

    private val _userProfile = mutableStateOf(UserProfileModel())
    val userProfile: State<UserProfileModel> = _userProfile

    private val _userData = mutableStateOf(com.example.doseymedicine.model.DoseyModel())
    val userData: State<com.example.doseymedicine.model.DoseyModel> = _userData

    fun addMedicine(
        name: String,
        desc: String,
        dosage: String,
        time: String,
        frequency: String,
        startDate: String,
        endDate: String,
        totalPills: Int,
        pillsLeft: Int,
        onResult: (Boolean, String) -> Unit
    ) {

        val newMed = MedicineModel(
            name = name,
            desc = desc,
            dosage = dosage,
            time = time,
            frequency = frequency,
            startDate = startDate,
            endDate = endDate,
            totalPills = totalPills,
            pillsLeft = pillsLeft,
            taken = false
        )

        repo.addMedicine(newMed) { success, message ->
            if (success) {
                loadMedicines()
            }
            onResult(success, message)
        }
    }

    fun markTaken(id: String) {
        repo.markTaken(id) { loadMedicines() }
    }

    fun getMedicineById(
        id: String,
        callback: (MedicineModel?) -> Unit
    ) {
        repo.getMedicineById(id, callback)
    }

//    edit
fun updateMedicine(
    medicineId: String,
    medicine: MedicineModel,
    callback: (Boolean) -> Unit)
{
        repo.updateMedicine(medicineId, medicine)
        {
            if (it) loadMedicines()
            callback(it)
        }

    }

//    delete
fun deleteMedicine(
    medicineId: String,
    callback: (Boolean) -> Unit
){
    repo.deleteMedicine(medicineId){
        success ->
        if (success){
            loadMedicines()
        }
        callback(success)
    }
}
    fun fetchUserProfile() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        repo.getUserProfile(userId) { profile ->
            if (profile != null) _userProfile.value = profile
        }
    }

    fun updateUserProfile(profile: UserProfileModel) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        repo.saveUserProfile(userId, profile) { success ->
            if (success) {
                _userProfile.value = profile.copy()
                fetchUserProfile()
            }
        }
    }

    fun fetchUserData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        repo.getUserData(userId) { user ->
            if (user != null) _userData.value = user
        }
    }

    fun updateUserData(firstName: String, lastName: String, callback: (Boolean) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val currentUserData = _userData.value
        val updatedUserData = currentUserData.copy(firstName = firstName, lastName = lastName)
        repo.updateUserData(userId, updatedUserData) { success ->
            if (success) {
                _userData.value = updatedUserData
            }
            callback(success)
        }
    }

    fun undoTaken(id: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        repo.getMedicineById(id) { med ->
            if (med != null) {
                val updatedMed = med.copy(
                    taken = false,
                    pillsLeft = med.pillsLeft + 1
                )
                updateMedicine(id, updatedMed) { success ->
                    if (success) loadMedicines()
                }
            }
        }
    }
}