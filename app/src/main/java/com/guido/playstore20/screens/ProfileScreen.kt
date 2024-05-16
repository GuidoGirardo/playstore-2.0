package com.guido.playstore20.screens

import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.guido.playstore20.firebase.getAppsProfile
import com.guido.playstore20.navigation.AppScreens
import com.guido.playstore20.ui.theme.Purple40
import com.guido.playstore20.ui.theme.contraste
import com.guido.playstore20.viewmodel.PlaystoreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: PlaystoreViewModel) {
    val apps by viewModel.appsListP.observeAsState(emptyList())
    viewModel.getAppsProfileVM(viewModel.currentUser.value.toString())

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var logo by remember { mutableStateOf<Uri?>(null) }
    var screenshot1 by remember { mutableStateOf<Uri?>(null) }
    var screenshot2 by remember { mutableStateOf<Uri?>(null) }
    var screenshot3 by remember { mutableStateOf<Uri?>(null) }
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
    val screenshot1Launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            screenshot1 = uri
        }
    val screenshot2Launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            screenshot2 = uri
        }
    val screenshot3Launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            screenshot3 = uri
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple40)
    ) {
        Column() {
            Text(
                text = "Hello ${viewModel.currentUser.value}!",
                color = contraste,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(contraste, RoundedCornerShape(8.dp))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
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
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("description") },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // logo
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .background(Purple40, RoundedCornerShape(8.dp))
                            .clickable {
                                logoLauncher.launch("image/*")
                            }
                    ) {
                        logo?.let { uri ->
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "logo",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } ?: run {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "select logo",
                                modifier = Modifier.align(Alignment.Center),
                                tint = contraste
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // screenshots
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(160.dp)
                            .background(Purple40, RoundedCornerShape(8.dp))
                            .clickable {
                                screenshot1Launcher.launch("image/*")
                            }
                    ) {
                        screenshot1?.let { uri ->
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "logo",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } ?: run {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "select screenshot",
                                modifier = Modifier.align(Alignment.Center),
                                tint = contraste
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(160.dp)
                            .background(Purple40, RoundedCornerShape(8.dp))
                            .clickable {
                                screenshot2Launcher.launch("image/*")
                            }
                    ) {
                        screenshot2?.let { uri ->
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "logo",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } ?: run {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "select screenshot",
                                modifier = Modifier.align(Alignment.Center),
                                tint = contraste
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(160.dp)
                            .background(Purple40, RoundedCornerShape(8.dp))
                            .clickable {
                                screenshot3Launcher.launch("image/*")
                            }
                    ) {
                        screenshot3?.let { uri ->
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "logo",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } ?: run {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "select screenshot",
                                modifier = Modifier.align(Alignment.Center),
                                tint = contraste
                            )
                        }
                    }
                }

                var isExpanded by remember { mutableStateOf(false) }
                var categoria by remember { mutableStateOf("") }

                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = it },
                    modifier = Modifier.padding(start = 16.dp).clip(RoundedCornerShape(16.dp))
                ) {
                    TextField(
                        value = categoria,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                        },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text("app")
                            },
                            onClick = {
                                categoria = "app"
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text("game")
                            },
                            onClick = {
                                categoria = "game"
                                isExpanded = false
                            }
                        )
                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (apkUri != null && logo != null && title != "" && description != "" && (screenshot1 != null || screenshot2 != null || screenshot3 != null) && categoria != "" && viewModel.currentUser.value != null) {
                        Button(onClick = {
                            viewModel.uploadAppViewModel(
                                apkUri!!,
                                context,
                                title,
                                description,
                                logo!!,
                                screenshot1 ?: Uri.EMPTY,
                                screenshot2 ?: Uri.EMPTY,
                                screenshot3 ?: Uri.EMPTY,
                                categoria,
                                viewModel.currentUser.value.toString()
                            )
                        }) {
                            Text(text = "upload")
                        }
                    }
                }
            }

            LazyColumn {
                // apps del usuario, que se actualicen automaticamente
                // cuando el usuario suba una nueva app
                items(apps) { app ->
                    // Aquí puedes mostrar cada elemento de la lista de aplicaciones
                    // Por ejemplo, si "title" es el campo que contiene el nombre de la aplicación:
                    val titulo = app["titulo"]?.toString() ?: ""
                    Text(titulo, modifier = Modifier.padding(start = 16.dp),
                        color = contraste)

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