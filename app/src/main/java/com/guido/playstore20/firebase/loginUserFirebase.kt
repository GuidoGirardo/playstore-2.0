package com.guido.playstore20.firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

fun loginUserFirebase(user:String, password:String, onSuccess: () -> Unit) {
    val db = Firebase.firestore
    db.collection("users")
        .whereEqualTo("user", user)
        .whereEqualTo("password", password)
        .get()
        .addOnSuccessListener { result ->
            if (!result.isEmpty) {
                onSuccess()
            }else{
                Log.i("xd", "no existe un user o password así papa")

            }
        }
        .addOnFailureListener { exception ->
            Log.i("xd", "Error getting documents.", exception)
        }
}