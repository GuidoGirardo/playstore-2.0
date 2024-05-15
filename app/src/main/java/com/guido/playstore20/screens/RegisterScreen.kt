package com.guido.playstore20.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.guido.playstore20.firebase.registerUserFirebase
import com.guido.playstore20.navigation.AppScreens
import com.guido.playstore20.ui.theme.Purple40
import com.guido.playstore20.ui.theme.contraste

@Composable
fun RegisterScreen(navController: NavController) {

    var user by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                // .background(backgroundApp)
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
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "REGISTER", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(18.dp))
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
            Text(text = "Email", fontSize = 22.sp, fontWeight = FontWeight.Light)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Email")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Password",
                fontSize = 22.sp, /* color = textColor, */
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Password")
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = { registerUserFirebase(user, email, password) },
                modifier = Modifier
                    .width(180.dp)
                    .height(60.dp)
            ) {
                Text(text = "register", fontSize = 22.sp, fontWeight = FontWeight.Normal)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "already have an account? login",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .clickable { navController.navigate(AppScreens.LoginScreen.route) }
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