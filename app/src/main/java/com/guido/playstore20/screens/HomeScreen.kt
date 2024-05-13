package com.guido.playstore20.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.guido.playstore20.navigation.AppScreens
import com.guido.playstore20.ui.theme.Purple40
import com.guido.playstore20.ui.theme.contraste
import com.guido.playstore20.viewmodel.PlaystoreViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: PlaystoreViewModel) {

    val appsList by viewModel.appsList.observeAsState(emptyList())
    var searchText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple40)
    ) {
        Column() {
            TextField(
                value = searchText,
                onValueChange = { newValue ->
                    searchText = newValue
                    viewModel.searchApp(newValue)
                },
                label = { Text("search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(32.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(appsList) { post ->
                    val apk = post["apk"]?.toString() ?: ""
                    val capturas = (post["capturas"] as? List<String>) ?: emptyList()
                    val categoria = post["categoria"]?.toString() ?: ""
                    val comentarios = (post["comentarios"] as? List<String>) ?: emptyList()
                    val descargas = post["descargas"]?.toString() ?: ""
                    val descripcion = post["descripcion"]?.toString() ?: ""
                    val logo = post["logo"]?.toString() ?: ""
                    val titulo = post["titulo"]?.toString() ?: ""
                    val empresa = post["empresa"]?.toString() ?: ""

                    Log.i(
                        "xd",
                        "$apk - $capturas - $categoria - $comentarios - " +
                                "$descargas - $descripcion - $logo - $titulo"
                    )

                    var isExpanded by remember { mutableStateOf(false) }

                    if (isExpanded) {
                        appItemApretado(
                            onClick = { isExpanded = false },
                            titulo = titulo,
                            descripcion = descripcion,
                            descargas = descargas,
                            logo = logo,
                            categoria = categoria,
                            empresa = empresa,
                            capturas = capturas,
                            comentarios = comentarios
                        )
                    } else {
                        appItem(
                            titulo = titulo,
                            descripcion = descripcion,
                            descargas = descargas,
                            logo = logo,
                            categoria = categoria,
                            onClick = { isExpanded = true }
                        )
                    }
                }
            }

        }
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "profile button",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .size(72.dp)
                .background(Purple40, RoundedCornerShape(50.dp))
                .scale(0.8f)
                .clickable { navController.navigate(AppScreens.ProfileScreen.route) },
                tint = contraste
        )
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun appItem(
    titulo: String,
    descripcion: String,
    descargas: String,
    logo: String,
    categoria: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(contraste)
                .padding(16.dp)
        ) {
            GlideImage(
                model = logo,
                contentDescription = "logo",
                modifier = Modifier
                    .size(88.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column() {
                Text(titulo)
                Text(descripcion)
                Row() {
                    Text(descargas)
                    Spacer(modifier = Modifier.width(32.dp))
                    Text(categoria)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun appItemApretado(
    titulo: String,
    descripcion: String,
    descargas: String,
    capturas: List<String>,
    logo: String,
    categoria: String,
    onClick: () -> Unit,
    empresa: String,
    comentarios: List<String>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(contraste)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column() {
                    GlideImage(
                        model = logo,
                        contentDescription = "logo",
                        modifier = Modifier
                            .size(88.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        empresa,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                Column(
                    modifier = Modifier
                        .width(250.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(descargas)
                    Button(
                        onClick = { /* TODO */ },
                        modifier = Modifier.width(180.dp)
                    ) {
                        Text("instalar")
                    }
                    Text(categoria)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column() {
                Text(titulo)
                Text(descripcion)
                Spacer(modifier = Modifier.height(16.dp))
                Row() {
                    capturas.forEach {
                        GlideImage(
                            model = it,
                            contentDescription = "capturas",
                            modifier = Modifier
                                .size(88.dp, 120.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("comentarios:")
                Spacer(modifier = Modifier.height(8.dp))

                Column() {
                    comentarios.forEach {
                        Text(it)
                    }
                }
            }
        }
    }
}