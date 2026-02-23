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

    fun addMedicine(medicine: MedicineModel, callback: (Boolean, String) -> Unit) {
        repo.addMedicine(medicine, callback)
    }

    fun markTaken(id: String) {
        repo.markTaken(id) { loadMedicines() }
    }
}