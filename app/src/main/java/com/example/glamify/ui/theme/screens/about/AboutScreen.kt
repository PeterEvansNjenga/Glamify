package com.example.glamify.ui.theme.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.glamify.R
import com.example.glamify.ui.theme.home_black

@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(home_black)
            .verticalScroll(rememberScrollState())
            .padding(15.dp)
    ) {
        // Icon row
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
                    containerColor = Color.Transparent
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.glamifyicon),
                    contentDescription = "top icon",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // How To Use section
        SectionHeader(text = "How To Use")
        Spacer(modifier = Modifier.height(5.dp))
        SimpleText(
            text = "Here at Glamify you can access a great variety of shoes available on the market."
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Buy Glamify section
        SectionHeader(text = "Buy Glamify")
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(horizontal = 45.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.buyscreen),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
        SimpleText(
            text = "You will find shoes which fellow users of Glamify are selling, uploaded with their quoted price and location. You can contact them through text messages or call them directly to negotiate on the price and try to strike a deal."
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Sell Glamify section
        SectionHeader(text = "Sell Glamify")
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(horizontal = 45.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sellscreen),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
        SimpleText(
            text = "You will be welcomed with a form that allows you to upload your product to the servers to allow buying of your shoes by other Glamify users. By clicking on \"View Shoes\" button you will be able to see all products that you have ever uploaded with your account and allows updating and deleting of the same."
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Menu section
        SectionHeader(text = "Menu")
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(horizontal = 45.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(modifier = Modifier.height(7.dp))
        SimpleText(
            text = "Here you will be able to do additional tasks such as creating an account, logging into a different account, and logging out."
        )
    }
}

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        color = Color.Cyan,
        fontSize = 28.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}

@Composable
fun SimpleText(text: String) {
    Text(
        text = text,
        fontSize = 17.sp,
        color = Color.Yellow
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun AboutScreenPreview() {
    AboutScreen()
}
