package com.example.doseymedicine.respo

import com.example.doseymedicine.model.MedicineModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MedicineRepoImpl: MedicineRepo {

    private val database = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    override fun addMedicine(
        medicine: MedicineModel,
        callback: (Boolean, String) -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: return

        val id = database.push().key!!
        val newMedicine = medicine.copy(id = id)

        database.child("medicines")
            .child(uid)
            .child(id)
            .setValue(newMedicine)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Medicine Added")
                } else {
                    callback(false, it.exception?.message ?: "Error")
                }
            }
    }

    override fun getMedicines(callback: (List<MedicineModel>) -> Unit) {
        val uid = auth.currentUser?.uid ?: return

        database.child("medicines")
            .child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<MedicineModel>()
                    for (child in snapshot.children) {
                        val med = child.getValue(MedicineModel::class.java)
                        med?.let { list.add(it) }
                    }
                    callback(list)
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    override fun markTaken(medicineId: String, callback: (Boolean) -> Unit) {
        val uid = auth.currentUser?.uid ?: return

        database.child("medicines")
            .child(uid)
            .child(medicineId)
            .child("taken")
            .setValue(true)
            .addOnCompleteListener {
                callback(it.isSuccessful)
            }
    }
}