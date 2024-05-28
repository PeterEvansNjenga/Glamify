@file:Suppress("DEPRECATION")

package com.example.glamify.ui.theme.screens.signup

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
import androidx.compose.material.icons.filled.Person
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
import com.example.glamify.navigation.LOGIN_URL
import com.example.glamify.ui.theme.WazitoECommerceTheme
import com.example.glamify.ui.theme.home_black
import com.example.glamify.ui.theme.main_green
import com.example.glamify.ui.theme.secondary_blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(home_black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        // Title
        Text(
            text = "Create an Account",
            textDecoration = TextDecoration.Underline,
            fontSize = 30.sp,
            color = Color.Cyan
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Username input field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text(text = "eg. Sam", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "username icon"
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = secondary_blue,
                unfocusedBorderColor = main_green,
                focusedLeadingIconColor = secondary_blue,
                unfocusedLeadingIconColor = main_green,
                cursorColor = Color.White,
                focusedLabelColor = secondary_blue,
                unfocusedLabelColor = main_green,
            ),
            label = { Text(text = "Username") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Email input field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text(text = "eg. abc@gmail.com", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "email icon"
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
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

        // Password input field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(text = "eg. maker#947", color = Color.White) },
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
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
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

        // Signup Button
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                if (name.isBlank() || email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Please input a value", Toast.LENGTH_SHORT).show()
                } else {
                    authViewModel.signup(name, email, password)
                }
            },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(main_green),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 0.dp)
        ) {
            Text(text = "Sign up")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Login text
        Text(
            text = "Have an account? Log in instead",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(LOGIN_URL)
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
fun SignupScreenPreview() {
    WazitoECommerceTheme {
        SignupScreen(navController = rememberNavController())
    }
}
