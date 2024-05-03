package com.guido.playstore20.screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.guido.playstore20.viewmodel.PlaystoreViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: PlaystoreViewModel){

    val appsList by viewModel.appsList.observeAsState(emptyList())

    LazyColumn {
        items(appsList) { post ->
            val apk = post["apk"].toString()
            val capturas = post["capturas"].toString()
            val categoria = post["categoria"].toString()
            val comentarios = post["comentarios"].toString()
            val descargas = post["descargas"].toString()
            val descripcion = post["descripcion"].toString()
            val logo = post["logo"].toString()
            val titulo = post["titulo"].toString()

            Log.i("xd", "$apk - $capturas - $categoria - $comentarios - $descargas - $descripcion - $logo - $titulo")
        }
    }

}