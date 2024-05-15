package com.guido.playstore20.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

fun registerUserFirebase(user: String, email: String, password: String) {
    val db = FirebaseFirestore.getInstance()

    db.collection("users")
        .whereEqualTo("user", user)
        .get()
        .addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { emailDocuments ->
                        if (emailDocuments.isEmpty) {
                            val newUser = hashMapOf(
                                "user" to user,
                                "email" to email,
                                "password" to password
                            )
                            db.collection("users")
                                .add(newUser)
                                .addOnSuccessListener { documentReference ->
                                    Log.i("xd", "DocumentSnapshot added with ID: ${documentReference.id}")

                                }
                                .addOnFailureListener { e ->
                                    Log.i("xd", "Error adding document", e)
                                }
                        } else {
                            Log.i("xd", "El correo electrónico ya está en uso.")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.i("xd", "Error getting documents: ", e)
                    }
            } else {
                Log.i("xd", "El usuario ya existe.")
            }
        }
        .addOnFailureListener { e ->
            Log.i("xd", "Error getting documents: ", e)
        }
}