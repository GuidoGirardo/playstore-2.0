package com.guido.playstore20.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guido.playstore20.firebase.deleteApp
import com.guido.playstore20.firebase.downloadApk
import com.guido.playstore20.firebase.getApps
import com.guido.playstore20.firebase.getAppsProfile
import com.guido.playstore20.firebase.uploadApp

class PlaystoreViewModel: ViewModel() {

    private val _appsList = MutableLiveData<List<Map<String, Any>>>()
    val appsList: LiveData<List<Map<String, Any>>> = _appsList
    private val allApps: MutableList<Map<String, Any>> = mutableListOf()

    var currentUser: MutableState<String?> = mutableStateOf(null)

    private val _appsListP = MutableLiveData<List<Map<String, Any>>>()
    val appsListP: LiveData<List<Map<String, Any>>> = _appsListP
    private val allAppsP: MutableList<Map<String, Any>> = mutableListOf()

    fun getAppsProfileVM(user: String) {
        getAppsProfile(user){ apps ->
            allAppsP.clear()
            allAppsP.addAll(apps)
            _appsListP.value = allAppsP
        }
    }

    fun deleteAppVM(titulo: String, context: Context){
        deleteApp(titulo, context)
    }

    init {
        getApps { apps ->
            allApps.clear()
            allApps.addAll(apps)
            _appsList.value = allApps
        }
    }

    fun searchApp(query: String) {
        val filteredApps = allApps.filter { app ->
            val titulo = app["titulo"]?.toString() ?: ""
            val descripcion = app["descripcion"]?.toString() ?: ""
            titulo.contains(query, ignoreCase = true) || descripcion.contains(query, ignoreCase = true)
        }
        _appsList.value = filteredApps
    }

    fun uploadAppViewModel(apkUri: Uri, context: Context, title: String, description: String, logo: Uri, screenshot1: Uri?, screenshot2: Uri?, screenshot3: Uri?, categoria: String, user: String, viewModel: PlaystoreViewModel){
        uploadApp(apkUri, context, title, description, logo, screenshot1, screenshot2, screenshot3, categoria, user, viewModel)
    }

    fun downloadAppViewModel(apkUrl: String, appName: String, context: Context, logo: String){
        downloadApk(apkUrl, appName, context, logo)
    }

}