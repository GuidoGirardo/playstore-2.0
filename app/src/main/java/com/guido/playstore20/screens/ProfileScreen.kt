package com.guido.playstore20.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.guido.playstore20.navigation.AppScreens
import com.guido.playstore20.ui.theme.Purple40
import com.guido.playstore20.ui.theme.contraste
import com.guido.playstore20.viewmodel.PlaystoreViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: PlaystoreViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var logo by remember { mutableStateOf<Uri?>(null) }
    var apkUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val apkLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            apkUri = uri
        }
    val logoLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            logo = uri
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple40)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(contraste, RoundedCornerShape(8.dp))
        ) {
            Row(modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { apkLauncher.launch("application/vnd.android.package-archive") }) {
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
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("title") },
                modifier = Modifier.padding(start = 16.dp).clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("description") },
                modifier = Modifier.padding(start = 16.dp).clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ){

                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .background(Color.Red, RoundedCornerShape(8.dp))
                        .clickable {
                            logoLauncher.launch("image/*")
                        }
                ) {
                    logo?.let { uri ->
                        // Si se ha seleccionado una imagen, muestra la imagen
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = "logo",
                            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } ?: run {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "select logo",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }




            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (apkUri != null && logo != null && title != "" && description != "") {
                    Button(onClick = { viewModel.uploadAppViewModel(apkUri!!, context) }) {
                        Text(text = "upload")
                    }
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