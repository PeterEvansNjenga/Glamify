package com.example.glamify.ui.theme.screens.products

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.glamify.data.Product
import com.example.glamify.models.ProductViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        val context = LocalContext.current
        val shoeRepository = remember { ProductViewModel(navController, context) }
        val emptyShoeState = remember { mutableStateOf(Product()) }
        val emptyShoesListState = remember { mutableStateListOf<Product>() }
        val shoes = shoeRepository.allProducts(emptyShoeState, emptyShoesListState)

        // Search query state
        val searchQuery by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
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
                    backgroundColor = MaterialTheme.colors.onPrimary
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

            // Filtered list of products
            val filteredShoes = if (searchQuery.isEmpty()) {
                shoes
            } else {
                shoes.filter {
                    it.name.contains(searchQuery, ignoreCase = true) ||
                            it.description.contains(searchQuery, ignoreCase = true)
                }
            }

            LazyColumn {
                items(filteredShoes) { product ->
                    ProductItem(product = product)
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 25.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.onSecondary
        ) {
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .padding(vertical = 9.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(product.imageUrl),
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
                        text = "Name: ${product.name}",
                        color = MaterialTheme.colors.background,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "Description: ${product.description}",
                        color = MaterialTheme.colors.background,
                        fontSize = 17.sp,
                    )
                    Spacer(modifier = Modifier.height(3.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Price: Ksh. ${product.price}",
                            fontSize = 17.sp,
                            color = MaterialTheme.colors.background
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colors.background
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = product.location,
                            color = MaterialTheme.colors.background,
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
                                        data = Uri.parse("smsto:${product.phoneno}")
                                        putExtra("sms_body", "Hello Seller,...?")
                                    }
                                    context.startActivity(smsIntent)
                                } catch (e: ActivityNotFoundException) {
                                    Toast.makeText(context, "No SMS app found.", Toast.LENGTH_SHORT).show()
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.error)
                        ) {
                            Row {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "Message Seller",
                                    tint = MaterialTheme.colors.error
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "Message Seller",
                                    color = MaterialTheme.colors.error
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            onClick = {
                                try {
                                    val callIntent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:${product.phoneno}")
                                    }
                                    context.startActivity(callIntent)
                                } catch (e: ActivityNotFoundException) {
                                    Toast.makeText(context, "No dialer app found.", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
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

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
