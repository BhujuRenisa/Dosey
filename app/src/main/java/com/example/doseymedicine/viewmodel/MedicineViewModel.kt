package com.example.doseymedicine.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doseymedicine.model.MedicineModel
import com.example.doseymedicine.respo.MedicineRepo
import com.example.doseymedicine.respo.MedicineRepoImpl

class MedicineViewModel : ViewModel() {
    private val repo: MedicineRepo = MedicineRepoImpl()
    private val _medicines = MutableLiveData<List<MedicineModel>>()
    val medicines: LiveData<List<MedicineModel>> = _medicines
    fun loadMedicines() {
        repo.getMedicines {
            _medicines.postValue(it)
        }
    }

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
}