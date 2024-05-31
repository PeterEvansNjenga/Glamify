@file:Suppress("LocalVariableName")

package com.example.glamify.ui.theme.screens.account

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.glamify.R
import com.example.glamify.models.AuthViewModel
import com.example.glamify.navigation.LOGIN_URL
import com.example.glamify.navigation.SIGNUP_URL

@Composable
fun AccountScreen(navController: NavHostController, ToggleTheme: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Account Screen",
            fontSize = 24.sp,
            color = colors.onBackground,
            modifier = Modifier.padding(16.dp)
        )
        val mContext = LocalContext.current
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Card holding icon
            Card(
                modifier = Modifier
                    .size(70.dp),
                shape = RoundedCornerShape(50),
                colors = CardDefaults.cardColors(
                    containerColor = colors.onBackground
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.glamifymenu),
                    contentDescription = "top icon",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "Account",
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.SansSerif,
            color = colors.onBackground
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Account details
        AccountItem(
            icon = Icons.Default.AccountBox,
            text = "Account Details"
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Create new account
        AccountCard(
            icon = Icons.Default.AddCircle,
            text = "Create New Account"
        ) {
            navController.navigate(SIGNUP_URL)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Log into another account
        AccountCard(
            icon = Icons.Default.AccountCircle,
            text = "Log Into Another Account"
        ) {
            navController.navigate(LOGIN_URL)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Log out of account
        AccountCard(
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            text = "Log Out"
        ) {
            Toast
                .makeText(mContext, "Logged out", Toast.LENGTH_SHORT)
                .show()
            val authViewModel = AuthViewModel(navController, mContext)
            authViewModel.logout()
        }
    }
}

@Composable
fun AccountItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.onBackground
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = text,
            fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun AccountCard(icon: ImageVector, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
            .height(50.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.onBackground
        )
    ) {
        Row(
            modifier = Modifier
                .height(50.dp)
                .padding(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colors.background
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                color = colors.background
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AccountScreenPreview() {
    AccountScreen(navController = rememberNavController(), ToggleTheme = {})
}
