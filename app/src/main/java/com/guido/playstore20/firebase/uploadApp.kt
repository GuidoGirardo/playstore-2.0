package com.guido.playstore20.firebase

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.guido.playstore20.viewmodel.PlaystoreViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun uploadApp(apkUri: Uri, context: Context, title: String, description: String, logoUri: Uri, screenshot1: Uri?, screenshot2: Uri?, screenshot3: Uri?, categoria: String, user:String, viewModel: PlaystoreViewModel) {
    Log.i("xd", screenshot1.toString())
    Log.i("xd", screenshot2.toString())
    Log.i("xd", screenshot3.toString())

    val screenshotUris = mutableListOf<Uri>()
    for (screenshot in listOf(screenshot1, screenshot2, screenshot3)) {
        screenshot?.let {
            if (it != Uri.EMPTY) {
                screenshotUris.add(it)
            }
        }
    }
    Log.i("xd", screenshotUris.toString())


    val firestore = FirebaseFirestore.getInstance()
    val appsCollection = firestore.collection("apps")

    // Verificar si el título ya existe en Firestore
    appsCollection.whereEqualTo("titulo", title).get().addOnCompleteListener { queryTask ->
        if (queryTask.isSuccessful) {
            val querySnapshot = queryTask.result
            if (querySnapshot != null && querySnapshot.isEmpty) {
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

                                // Subir capturas de pantalla y obtener sus URLs
                                val screenshotUrls = mutableListOf<String>()
                                val screenshotTasks = mutableListOf<Task<Uri>>()
                                for (screenshotUri in screenshotUris) {
                                    val screenshotRef = storageRef.child("screenshots/$title/${screenshotUri.lastPathSegment}")
                                    val screenshotUploadTask = screenshotRef.putFile(screenshotUri)
                                    screenshotTasks.add(screenshotUploadTask.continueWithTask { task ->
                                        if (!task.isSuccessful) {
                                            task.exception?.let {
                                                throw it
                                            }
                                        }
                                        screenshotRef.downloadUrl
                                    })
                                }

                                Tasks.whenAllComplete(screenshotTasks).addOnCompleteListener { screenshotTask ->
                                    if (screenshotTask.isSuccessful) {
                                        for (result in screenshotTask.result) {
                                            if (result.isSuccessful) {
                                                val screenshotUri = result.result
                                                screenshotUri?.let {
                                                    screenshotUrls.add(it.toString())
                                                }
                                            }
                                        }

                                        // Guardar los datos en Firestore
                                        val appData = hashMapOf(
                                            "titulo" to title,
                                            "descripcion" to description,
                                            "categoria" to categoria,
                                            "empresa" to user,
                                            "apk" to apkDownloadUri.toString(),
                                            "logo" to logoDownloadUri.toString(),
                                            "capturas" to screenshotUrls,
                                            "descargas" to 0
                                        )

                                        appsCollection.add(appData).addOnSuccessListener {
                                            Toast.makeText(context, "App data uploaded successfully", Toast.LENGTH_LONG).show()
                                            viewModel.getAppsProfileVM(viewModel.currentUser.value.toString())
                                        }.addOnFailureListener { exception ->
                                            // Handle unsuccessful app data insertion
                                            Toast.makeText(context, "Error uploading app data: $exception", Toast.LENGTH_SHORT).show()
                                            viewModel.getAppsProfileVM(viewModel.currentUser.value.toString())
                                        }
                                    } else {
                                        // Handle screenshot upload failure
                                        Toast.makeText(context, "Error uploading screenshots: ${screenshotTask.exception}", Toast.LENGTH_SHORT).show()
                                    }
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