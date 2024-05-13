package com.guido.playstore20.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.guido.playstore20.navigation.AppScreens
import com.guido.playstore20.ui.theme.Purple40
import com.guido.playstore20.ui.theme.contraste
import com.guido.playstore20.viewmodel.PlaystoreViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: PlaystoreViewModel) {
    var apkUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            apkUri = uri
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple40)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(contraste, RoundedCornerShape(8.dp))
        ) {
            Row(modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { launcher.launch("application/vnd.android.package-archive") }) {
                    Text(text = "select apk")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (apkUri != null) {
                        "${apkUri?.lastPathSegment}"
                    } else {
                        "select an .apk"
                    }
                )
            }
            if (apkUri != null) {
                Button(onClick = { uploadApkToFirebase(apkUri!!, context) }) {
                    Text(text = "upload")
                }
            }
        }
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "home button",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .size(72.dp)
                .background(Purple40, RoundedCornerShape(50.dp))
                .scale(0.8f)
                .clickable { navController.navigate(AppScreens.HomeScreen.route) },
            tint = contraste
        )
    }
}

fun uploadApkToFirebase(apkUri: Uri, context: Context) {
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