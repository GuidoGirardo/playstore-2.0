package com.guido.playstore20.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guido.playstore20.firebase.getApps

class PlaystoreViewModel: ViewModel() {

    private val _appsList = MutableLiveData<List<Map<String, Any>>>()
    val appsList: LiveData<List<Map<String, Any>>> = _appsList

    init {
        getApps { apps ->
            _appsList.value = apps
        }
    }

}