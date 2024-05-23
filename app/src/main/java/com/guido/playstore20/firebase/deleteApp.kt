package com.guido.playstore20.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

fun deleteApp(titulo: String, context: Context) {
    val firestore = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()

    // Eliminar de Firestore "apps":
    firestore.collection("apps")
        .whereEqualTo("titulo", titulo)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                firestore.collection("apps").document(document.id).delete()
                    .addOnSuccessListener {

                    }
                    .addOnFailureListener { e ->

                    }
            }
        }
        .addOnFailureListener { e ->
        }

    // Eliminar del Storage:
    // 1- "apks/titulo"
    val apkRef = storage.reference.child("apks/$titulo")
    apkRef.delete().addOnSuccessListener {

    }.addOnFailureListener { e ->

    }

    val screenshotsRef = storage.reference.child("screenshots/$titulo")
    screenshotsRef.listAll().addOnSuccessListener { listResult ->
        if (listResult.items.isEmpty()) {
            Log.d("DeleteApp", "No se encontraron screenshots para borrar en la carpeta: screenshots/$titulo")
            Toast.makeText(context, "No se encontraron screenshots para borrar.", Toast.LENGTH_SHORT).show()
        }
        for (item in listResult.items) {
            item.delete().addOnSuccessListener {
                Toast.makeText(context, "Screenshot borrada: ${item.name}", Toast.LENGTH_SHORT).show()
                Log.d("DeleteApp", "Screenshot borrada exitosamente: ${item.name}")
            }.addOnFailureListener { e ->
                val errorMessage = "Error al borrar screenshot: ${item.name}, error: $e"
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                Log.e("DeleteApp", errorMessage, e)
            }
        }
    }.addOnFailureListener { e ->
        val errorMessage = "Error al listar screenshots en screenshots/$titulo: $e"
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        Log.e("DeleteApp", errorMessage, e)
    }

    // 3- "logo/titulo"
    val logoRef = storage.reference.child("logos/$titulo")
    logoRef.delete().addOnSuccessListener {

    }.addOnFailureListener { e ->

    }
}