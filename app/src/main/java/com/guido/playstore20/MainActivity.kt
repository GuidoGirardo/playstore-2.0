package com.guido.playstore20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.guido.playstore20.navigation.AppNavigation
import com.guido.playstore20.ui.theme.Playstore20Theme
import com.guido.playstore20.viewmodel.PlaystoreViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: PlaystoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Playstore20Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(viewModel = viewModel, context = applicationContext)
                }
            }
        }
    }
}