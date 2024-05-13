package com.guido.playstore20.firebase

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.storage.storage

fun uploadApp(apkUri: Uri, context: Context) {
    val storageRef = Firebase.storage.reference.child("apks/caca")
    val uploadTask = storageRef.putFile(apkUri)

    uploadTask.continueWithTask { task ->
        if (!task.isSuccessful) {
            task.exception?.let {
                throw it
            }
        }
        storageRef.downloadUrl
    }.addOnCompleteListener { task: Task<Uri> ->
        if (task.isSuccessful) {
            val downloadUri = task.result
            // Handle successful upload
            Toast.makeText(context, "APK uploaded successfully", Toast.LENGTH_SHORT).show()
            // Muestra el enlace de descarga en un Toast o en otro lugar
            Toast.makeText(context, "Download link: $downloadUri", Toast.LENGTH_LONG).show()
            Log.i("xd", downloadUri.toString())
        } else {
            // Handle unsuccessful upload
            Toast.makeText(context, "Upload failed: ${task.exception}", Toast.LENGTH_SHORT).show()
        }
    }
}