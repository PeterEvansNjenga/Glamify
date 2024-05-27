package com.example.glamify.ui.theme.screens.products

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import coil.compose.rememberAsyncImagePainter
import com.example.glamify.R
import com.example.glamify.models.ProductViewModel
import com.example.glamify.data.Product
import com.example.glamify.ui.theme.WazitoECommerceTheme
import com.example.glamify.ui.theme.card_green
import com.example.glamify.ui.theme.home_black
import com.example.glamify.ui.theme.main_green
import com.example.glamify.ui.theme.secondary_blue

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(home_black)
    ) {
        val context = LocalContext.current
        val shoeRepository = remember { ProductViewModel(navController, context) }
        val emptyShoeState = remember { mutableStateOf(Product()) }
        val emptyShoesListState = remember { mutableStateListOf<Product>() }
        val shoes = shoeRepository.allProducts(emptyShoeState, emptyShoesListState)

        Column(
            modifier = Modifier
                .background(home_black)
                .fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier.size(75.dp),
                    shape = RoundedCornerShape(50),
                    colors = CardDefaults.cardColors(containerColor = card_green)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.glamifybuy),
                        contentDescription = "top icon",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Buy Shoes",
                fontSize = 26.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
                color = Color.Cyan
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {
                items(shoes) {
                    ProductItem(
                        name = it.name,
                        description = it.description,
                        price = it.price,
                        phoneno = it.phoneno,
                        location = it.location,
                        shoeImage = it.imageUrl,
                    )
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    name: String,
    description: String,
    price: String,
    phoneno: String,
    location: String,
    shoeImage: String
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 25.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = card_green)
        ) {
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .padding(vertical = 9.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(shoeImage),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                    )
                }
                Column {
                    Text(
                        text = "Name: $name",
                        color = secondary_blue,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "Description: $description",
                        color = secondary_blue,
                        fontSize = 17.sp,
                    )
                    Spacer(modifier = Modifier.height(3.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Price: Ksh. $price",
                            fontSize = 17.sp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "Negotiable",
                                color = secondary_blue,
                                fontSize = 16.sp,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(3.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = secondary_blue
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = location,
                            color = secondary_blue,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Row(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedButton(
                            onClick = {
                                try {
                                    val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                                        data = Uri.parse("smsto:$phoneno")
                                        putExtra("sms_body", "Hello Seller,...?")
                                    }
                                    context.startActivity(smsIntent)
                                } catch (e: ActivityNotFoundException) {
                                    Toast.makeText(context, "No SMS app found.", Toast.LENGTH_SHORT).show()
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Row {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "Message Seller",
                                    tint = main_green
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "Message Seller",
                                    color = main_green
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    try {
                                        val callIntent = Intent(Intent.ACTION_DIAL).apply {
                                            data = Uri.parse("tel:$phoneno")
                                        }
                                        context.startActivity(callIntent)
                                    } catch (e: ActivityNotFoundException) {
                                        Toast.makeText(context, "No dialer app found.", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(main_green),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row {
                                    Icon(imageVector = Icons.Default.Call, contentDescription = "Call Seller")
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "Call Seller",
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    WazitoECommerceTheme {
        HomeScreen(navController = rememberNavController())
    }
}
