package com.example.doseymedicine.Respo

import com.example.doseymedicine.Model.DoseyModel
import com.google.firebase.Firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class AuthRepoImpl : AuthRepo {
    val auth: FirebaseAuth= FirebaseAuth.getInstance()
    val database: FirebaseDatabase= FirebaseDatabase.getInstance()
    val ref: DatabaseReference= database.getReference("Users")
    override fun login(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    callback(true,"Login sucess")
                }else{
                    callback(false,"${it.exception?.message}")
                }
            }
    }

    override fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(
                        true,
                        "Registration successful",
                        auth.currentUser?.uid ?: ""
                    )
                } else {
                    callback(false, it.exception?.message ?: "Registration failed", "")
                }
            }
    }

    override fun addUserToDatabase(
        userId: String,
        model: DoseyModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(userId).setValue(model)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "User added successfully")
                } else {
                    callback(false, it.exception?.message ?: "Failed to save user")
                }
            }
    }

    override fun forgotPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    callback(true,"Reset email sent to $email")
                }else{
                    callback(false,"${it.exception?.message}")
                }
            }
    }

    override fun deleteAccount(
        userId: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(userId).removeValue()
                    .addOnCompleteListener {
                if (it.isSuccessful) {
                    auth.currentUser?.delete()
                    callback(true, "Account deleted successfully")
                } else {
                    callback(false, it.exception?.message ?: "Delete failed")
                }
            }
    }

    override fun editProfile(
        userId: String,
        model: DoseyModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(userId).updateChildren(model.toMap())
                    .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Profile updated successfully")
                } else {
                    callback(false, it.exception?.message ?: "Update failed")
                }
            }
    }
    override fun getUserById(
        userId: String,
        callback: (Boolean, String, DoseyModel?) -> Unit
    ) {
        ref.child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(DoseyModel::class.java)
                        callback(true, "User fetched", user)
                    } else {
                        callback(false, "User not found", null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(false, error.message, null)
                }
            })
    }
}


//    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
//    private val ref: DatabaseReference = database.getReference("Users")
//
//    // Login
//    override fun login(
//        email: String,
//        password: String,
//        callback: (Boolean, String) -> Unit
//    ) {
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    callback(true, "Login successful")
//                } else {
//                    callback(false, it.exception?.message ?: "Login failed")
//                }
//            }
//    }
//
//    // Register
//    override fun register(
//        email: String,
//        password: String,
//        callback: (Boolean, String, String) -> Unit
//    ) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    callback(
//                        true,
//                        "Registration successful",
//                        auth.currentUser?.uid ?: ""
//                    )
//                } else {
//                    callback(false, it.exception?.message ?: "Registration failed", "")
//                }
//            }
//    }
//
//    // Save User Data
//    override fun addUserToDatabase(
//        userId: String,
//        model: DoseyModel,
//        callback: (Boolean, String) -> Unit
//    ) {
//        ref.child(userId).setValue(model)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    callback(true, "User added successfully")
//                } else {
//                    callback(false, it.exception?.message ?: "Failed to save user")
//                }
//            }
//    }
//
//    // Forgot Password
//    override fun forgotPassword(
//        email: String,
//        callback: (Boolean, String) -> Unit
//    ) {
//        auth.sendPasswordResetEmail(email)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    callback(true, "Reset email sent to $email")
//                } else {
//                    callback(false, it.exception?.message ?: "Failed to send email")
//                }
//            }
//    }
//
//    // Delete Account
//    override fun deleteAccount(
//        userId: String,
//        callback: (Boolean, String) -> Unit
//    ) {
//        ref.child(userId).removeValue()
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    auth.currentUser?.delete()
//                    callback(true, "Account deleted successfully")
//                } else {
//                    callback(false, it.exception?.message ?: "Delete failed")
//                }
//            }
//    }
//
//    //  Edit Profile
//    override fun editProfile(
//        userId: String,
//        model: DoseyModel,
//        callback: (Boolean, String) -> Unit
//    ) {
//        ref.child(userId).updateChildren(model.toMap())
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    callback(true, "Profile updated successfully")
//                } else {
//                    callback(false, it.exception?.message ?: "Update failed")
//                }
//            }
//    }
//
//    //  Get User by ID
//    override fun getUserById(
//        userId: String,
//        callback: (Boolean, String, DoseyModel?) -> Unit
//    ) {
//        ref.child(userId)
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if (snapshot.exists()) {
//                        val user = snapshot.getValue(DoseyModel::class.java)
//                        callback(true, "User fetched", user)
//                    } else {
//                        callback(false, "User not found", null)
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    callback(false, error.message, null)
//                }
//            })
//    }
//}
