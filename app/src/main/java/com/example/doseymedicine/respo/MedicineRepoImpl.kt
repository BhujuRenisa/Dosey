package com.example.doseymedicine.respo

import com.example.doseymedicine.model.MedicineModel
import com.example.doseymedicine.model.UserProfileModel
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

//    Decrease pill acutomatically
    override fun markTaken(medicineId: String, callback: (Boolean) -> Unit) {
        val uid = auth.currentUser?.uid ?: return

        val medRef = database.child("medicines")
            .child(uid)
            .child(medicineId)

        medRef.get().addOnSuccessListener { snapshot ->
            val med = snapshot.getValue(MedicineModel::class.java)

            if (med != null && med.pillsLeft > 0) {

                val updates = mapOf(
                    "taken" to true,
                    "pillsLeft" to med.pillsLeft - 1
                )

                medRef.updateChildren(updates)
                    .addOnCompleteListener {
                        callback(it.isSuccessful)
                    }
            } else {
                callback(false)
            }
        }
    }

    override fun getMedicineById(
        medicineId: String,
        callback: (MedicineModel?) -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: return

        database.child("medicines")
            .child(uid)
            .child(medicineId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val medicine = snapshot.getValue(MedicineModel::class.java)
                    callback(medicine)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }
            })
    }

    override fun updateMedicine(
        medicineId: String,
        updatedMed: MedicineModel,
        callback: (Boolean) -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: return

        database.child("medicines")
            .child(uid)
            .child(medicineId)
            .setValue(updatedMed)
            .addOnCompleteListener {
                callback(it.isSuccessful)
            }
    }

    override fun deleteMedicine(
        medicineId: String,
        callback: (Boolean) -> Unit
    ) {
        val uid =auth.currentUser?.uid ?: return
        database.child("medicines")
            .child(uid)
            .child(medicineId)
            .removeValue()
            .addOnCompleteListener {
                callback(it.isSuccessful)
            }
    }

    override fun saveUserProfile(
        userId: String,
        profile: UserProfileModel,
        callback: (Boolean) -> Unit
    ) {
        val profileRef = database.child("users")
            .child(userId)
            .child("profile")

        profileRef.setValue(profile)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
    override fun getUserProfile(
        userId: String,
        callback: (UserProfileModel?) -> Unit
    ) {
        database.child("users").child(userId)
            .child("profile")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snapshot = task.result
                    val profile = snapshot.getValue(UserProfileModel::class.java)
                    callback(profile)
                } else {
                    callback(null)
                }
            }
    }
}