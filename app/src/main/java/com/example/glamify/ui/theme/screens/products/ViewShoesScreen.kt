package com.example.glamify.ui.theme.screens.products

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.glamify.data.Product
import com.example.glamify.models.ProductViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ViewShoesScreen(navController: NavHostController) {
    MaterialTheme {
        val context = LocalContext.current
        val shoeRepository = remember { ProductViewModel(navController, context) }

        val emptyShoeState = remember { mutableStateOf(Product("", "", "", "", "", "", "", "")) }
        val shoesListState = remember { mutableStateListOf<Product>() }

        val shoes = shoeRepository.allProducts(emptyShoeState, shoesListState)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
        ) {
            var isPopupDismissed by remember { mutableStateOf(false) }

            if (!isPopupDismissed) {
                ScreenWithPopup {
                    isPopupDismissed = true
                }
            } else {
                Spacer(modifier = Modifier.height(15.dp))

                val currentUser = FirebaseAuth.getInstance().currentUser
                val currentUserId = currentUser?.uid

                LazyColumn {
                    items(shoes) { shoe ->
                        if (shoe.userId == currentUserId) {
                            ShoeItem(
                                product = shoe,
                                shoeRepository = shoeRepository
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShoeItem(
    product: Product,
    shoeRepository: ProductViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        colors = CardDefaults.cardColors(containerColor = colors.onSecondary)
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(vertical = 9.dp)
            ) {
                Image(
                    painter = rememberImagePainter(product.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    contentScale = ContentScale.FillBounds
                )
            }
            ShoeDetails(
                name = product.name,
                description = product.description,
                price = product.price,
                location = product.location
            )
            DeleteButton(productId = product.id, shoeRepository = shoeRepository)
        }
    }
}

@Composable
fun ShoeDetails(name: String, description: String, price: String, location: String) {
    Column {
        Text(
            text = "Name: $name",
            color = colors.background,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = "Description: $description",
            color = colors.background,
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.height(3.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Price: Ksh. $price", fontSize = 17.sp, color = colors.background,)
        }
        Spacer(modifier = Modifier.height(3.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = colors.background)
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = location,
                color = colors.background,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun DeleteButton(productId: String, shoeRepository: ProductViewModel) {
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    shoeRepository.deleteProduct(productId)
                },
                colors = ButtonDefaults.buttonColors(colors.error),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = "Delete", color = colors.onBackground)
                }
            }
        }
    }
}

@Composable
fun ScreenWithPopup(onDismiss: () -> Unit) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        Dialog(onDismissRequest = {
            showDialog = false
            onDismiss()
        }) {
            Box(
                modifier = Modifier
                    .padding(13.dp)
                    .background(colors.surface)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Hi. Here you can view all the shoes you have ever uploaded and have the ability to delete and update the shoe details.",
                        fontSize = 17.sp,
                        color = colors.onSurface
                    )
                    Text(
                        text = "NOTE: The only shoes you'll see are the ones that YOU have posted.",
                        fontSize = 17.sp,
                        color = colors.onSurface
                    )
                    Button(
                        onClick = {
                            showDialog = false
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 7.dp),
                        colors = ButtonDefaults.buttonColors(colors.error),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Okay", fontSize = 17.sp)
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ViewShoesScreenPreview() {
    ViewShoesScreen(navController = rememberNavController())
}
