package com.guido.playstore20.firebase

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.net.URLEncoder

fun uploadApp(apkUri: Uri, context: Context, title: String, description: String, logoUri: Uri) {
    val firestore = FirebaseFirestore.getInstance()
    val appsCollection = firestore.collection("apps")

    // Verificar si el título ya existe en Firestore
    appsCollection.whereEqualTo("titulo", title).get().addOnCompleteListener { queryTask ->
        if (queryTask.isSuccessful) {
            val querySnapshot = queryTask.result
            if (querySnapshot != null && querySnapshot.isEmpty) {
                // No existe ningún documento con el mismo título, podemos proceder con la carga del APK y el logo
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference
                val apkRef = storageRef.child("apks/$title")
                val logoRef = storageRef.child("logos/$title")

                // Subir el APK
                val apkUploadTask = apkRef.putFile(apkUri)

                apkUploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    apkRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val apkDownloadUri = task.result

                        // Subir el logo
                        val logoUploadTask = logoRef.putFile(logoUri)

                        logoUploadTask.continueWithTask { task ->
                            if (!task.isSuccessful) {
                                task.exception?.let {
                                    throw it
                                }
                            }
                            logoRef.downloadUrl
                        }.addOnCompleteListener { logoTask ->
                            if (logoTask.isSuccessful) {
                                val logoDownloadUri = logoTask.result

                                // Guardar los datos en Firestore
                                val appData = hashMapOf(
                                    "titulo" to title,
                                    "descripcion" to description,
                                    "apk" to apkDownloadUri.toString(),
                                    "logo" to logoDownloadUri.toString()
                                )

                                appsCollection.add(appData).addOnSuccessListener {
                                    // Handle successful app data insertion
                                    Toast.makeText(context, "App data uploaded successfully", Toast.LENGTH_SHORT).show()
                                }.addOnFailureListener { exception ->
                                    // Handle unsuccessful app data insertion
                                    Toast.makeText(context, "Error uploading app data: $exception", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                // Handle logo upload failure
                                Toast.makeText(context, "Error uploading logo: ${logoTask.exception}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        // Handle APK upload failure
                        Toast.makeText(context, "Error uploading APK: ${task.exception}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Ya existe un documento con el mismo título
                Toast.makeText(context, "A document with the same title already exists", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Handle query failure
            Toast.makeText(context, "Error querying Firestore: ${queryTask.exception}", Toast.LENGTH_SHORT).show()
        }
    }
}