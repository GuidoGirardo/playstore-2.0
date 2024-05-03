package com.guido.playstore20.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.guido.playstore20.viewmodel.PlaystoreViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: PlaystoreViewModel) {

    val appsList by viewModel.appsList.observeAsState(emptyList())

    LazyColumn {
        items(appsList) { post ->
            val apk = post["apk"]?.toString() ?: ""
            val capturas = (post["capturas"] as? List<String>) ?: emptyList()
            val categoria = post["categoria"]?.toString() ?: ""
            val comentarios = post["comentarios"]?.toString() ?: ""
            val descargas = post["descargas"]?.toString() ?: ""
            val descripcion = post["descripcion"]?.toString() ?: ""
            val logo = post["logo"]?.toString() ?: ""
            val titulo = post["titulo"]?.toString() ?: ""

            Log.i("xd",
                "$apk - $capturas - $categoria - $comentarios - " +
                        "$descargas - $descripcion - $logo - $titulo")

            var isExpanded by remember { mutableStateOf(false) }

            if(isExpanded){
                appItemApretado()
            }else {
                appItem(
                    titulo = titulo,
                    descripcion = descripcion,
                    descargas = descargas,
                    capturas = capturas,
                    logo = logo,
                    categoria = categoria,
                    onClick = { isExpanded = true }
                )
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun appItem(
    titulo: String,
    descripcion: String,
    descargas: String,
    capturas: List<String>,
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
                .background(Color.Cyan)
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
                Row(){
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
    /* titulo: String,
    descripcion: String,
    descargas: String,
    capturas: List<String>,
    logo: String,
    categoria: String,
    onBackPress: () -> Unit */
) {
    Text("apretado :)")
}