package com.example.glamify.ui.theme.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.glamify.models.AuthViewModel
import com.example.glamify.navigation.SIGNUP_URL
import com.example.glamify.ui.theme.home_black
import com.example.glamify.ui.theme.main_green
import com.example.glamify.ui.theme.secondary_blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(home_black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = "Log into Account",
            textDecoration = TextDecoration.Underline,
            fontSize = 30.sp,
            color = Color.Cyan
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Email TextField
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text(text = "eg. abc@example.com", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "email icon"
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                focusedBorderColor = secondary_blue,
                unfocusedBorderColor = main_green,
                focusedLeadingIconColor = secondary_blue,
                unfocusedLeadingIconColor = main_green,
                cursorColor = Color.White,
                focusedLabelColor = secondary_blue,
                unfocusedLabelColor = main_green,
            ),
            label = { Text(text = "Email") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Password TextField
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(text = "password", color = Color.White) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock"
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                focusedBorderColor = secondary_blue,
                unfocusedBorderColor = main_green,
                focusedLeadingIconColor = secondary_blue,
                unfocusedLeadingIconColor = main_green,
                cursorColor = Color.White,
                focusedLabelColor = secondary_blue,
                unfocusedLabelColor = main_green,
            ),
            label = { Text(text = "Password") },
        )

        Spacer(modifier = Modifier.height(13.dp))

        val context = LocalContext.current
        val authViewModel = remember { AuthViewModel(navController, context) }

        // Login Button
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Please input a value", Toast.LENGTH_SHORT).show()
                } else {
                    authViewModel.login(email, password)
                }
            },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(main_green),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 0.dp)
        ) {
            Text(text = "Log in")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Create account text
        Text(
            text = "Don't have an account? Create one",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(SIGNUP_URL)
                },
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            color = main_green
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}
