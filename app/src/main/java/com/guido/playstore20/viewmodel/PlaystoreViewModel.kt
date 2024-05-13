package com.guido.playstore20.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guido.playstore20.firebase.downloadApk
import com.guido.playstore20.firebase.getApps
import com.guido.playstore20.firebase.uploadApp

class PlaystoreViewModel: ViewModel() {

    private val _appsList = MutableLiveData<List<Map<String, Any>>>()
    val appsList: LiveData<List<Map<String, Any>>> = _appsList

    private val allApps: MutableList<Map<String, Any>> = mutableListOf()

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

    fun uploadAppViewModel(apkUri: Uri, context: Context, title: String, description: String, logo: Uri){
        uploadApp(apkUri, context, title, description, logo)
    }

    fun downloadAppViewModel(apkUrl: String, appName: String, context: Context, logo: String){
        downloadApk(apkUrl, appName, context, logo)
    }

}