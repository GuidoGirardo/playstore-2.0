package com.guido.playstore20.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.guido.playstore20.firebase.loginUserFirebase
import com.guido.playstore20.navigation.AppScreens
import com.guido.playstore20.ui.theme.Purple40
import com.guido.playstore20.ui.theme.contraste
import com.guido.playstore20.viewmodel.PlaystoreViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: PlaystoreViewModel){

    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /* Image(
            painter = painterResource(id = R.drawable.loginregister),
            contentDescription = "Descripción de tu imagen",
            modifier = Modifier
                .width(370.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(30.dp)),
            contentScale = ContentScale.Crop
        ) */
        Spacer(modifier = Modifier.height(22.dp))
        Text(text = "LOGIN", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "User", fontSize = 22.sp, fontWeight = FontWeight.Light)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = user,
            onValueChange = { user = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("User")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Password", fontSize = 22.sp, fontWeight = FontWeight.Light)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Password")
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                loginUserFirebase(user, password) {
                    viewModel.currentUser.value = user.toString()
                    navController.navigate(AppScreens.ProfileScreen.route)
                }
                Log.i("xd", viewModel.currentUser.toString())
            },
            modifier = Modifier
                .width(180.dp)
                .height(60.dp)
        ) {
            Text(text = "login", fontSize = 22.sp, fontWeight = FontWeight.Normal)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "don't have an account? register",
            fontSize = 20.sp,
            // color = wowColor,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable { navController.navigate(AppScreens.RegisterScreen.route) }
        )

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