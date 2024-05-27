@file:Suppress("DEPRECATION")

package com.example.glamify.ui.theme.screens.products

import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.glamify.R
import com.example.glamify.models.ProductViewModel
import com.example.glamify.ui.theme.WazitoECommerceTheme
import com.example.glamify.ui.theme.card_green
import com.example.glamify.ui.theme.home_black
import com.example.glamify.ui.theme.main_green
import com.example.glamify.ui.theme.secondary_blue

@Composable
fun AddShoesScreen(navController: NavHostController) {
    var shoeName by remember { mutableStateOf("") }
    var shoeDescription by remember { mutableStateOf("") }
    var shoePrice by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(home_black)
            .verticalScroll(rememberScrollState())
    ) {
        CenteredIconCard()
        Spacer(modifier = Modifier.height(7.dp))
        TitleText("Sell Shoes")
        Spacer(modifier = Modifier.height(7.dp))
        InputField(
            value = shoeName,
            onValueChange = { shoeName = it },
            label = "Shoe Name",
            placeholder = "e.g., Nike",
            leadingIcon = Icons.Default.Star
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(
            value = shoeDescription,
            onValueChange = { shoeDescription = it },
            label = "Shoe Description",
            placeholder = "e.g., Fashionable wear...",
            leadingIcon = Icons.Default.Info
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(
            value = shoePrice,
            onValueChange = { shoePrice = it },
            label = "Shoe Price (Ksh.)",
            placeholder = "e.g., ksh.5000",
            leadingIcon = Icons.Default.ArrowForward,
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = "Phone Number",
            placeholder = "e.g., 0700000000",
            leadingIcon = Icons.Default.Call,
            keyboardType = KeyboardType.Phone
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(
            value = location,
            onValueChange = { location = it },
            label = "Current Location",
            placeholder = "e.g., EastLeigh, Nairobi",
            leadingIcon = Icons.Default.LocationOn
        )
        Spacer(modifier = Modifier.height(20.dp))
        ImagePicker(
            imageUri = imageUri,
            onImageSelected = { imageUri = it }
        )
        Spacer(modifier = Modifier.height(20.dp))
        UploadButton(
            isUploading = isUploading,
            onClick = {
                if (validateInputs(shoeName, shoeDescription, shoePrice, phoneNumber, location, imageUri)) {
                    isUploading = true
                    val shoeRepository = ProductViewModel(navController, context)
                    shoeRepository.uploadProduct(
                        shoeName.trim(),
                        shoeDescription.trim(),
                        shoePrice.trim(),
                        phoneNumber.trim(),
                        location.trim(),
                        imageUri!!
                    )
                    isUploading = false
                }
            }
        )
    }
}

@Composable
fun CenteredIconCard() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.size(70.dp),
            shape = RoundedCornerShape(50),
            colors = CardDefaults.cardColors(containerColor = card_green)
        ) {
            Image(
                painter = painterResource(id = R.drawable.glamifysell),
                contentDescription = "top icon",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        fontSize = 27.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.SansSerif,
        color = Color.Cyan
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder, color = Color.White) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
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
        textStyle = TextStyle(color = Color.White),
        label = { Text(text = label) }
    )
}

@Composable
fun ImagePicker(
    imageUri: Uri?,
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current
    var hasImage by remember { mutableStateOf(imageUri != null) }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            hasImage = true
            onImageSelected(uri)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (hasImage && imageUri != null) {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Selected image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .padding(vertical = 5.dp)
            )
        }

        Button(
            onClick = { imagePicker.launch("image/*") },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(card_green),
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            border = BorderStroke(1.dp, main_green)
        ) {
            Text(
                text = "Select Shoe Image",
                color = secondary_blue,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun UploadButton(isUploading: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(main_green),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 20.dp)
    ) {
        if (isUploading) {
            CircularProgressIndicator(color = Color.White)
        } else {
            Text(
                text = "Upload",
                fontSize = 17.sp
            )
        }
    }
}

fun validateInputs(
    name: String,
    description: String,
    price: String,
    phoneNo: String,
    location: String,
    imageUri: Uri?
): Boolean {
    return name.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() &&
            phoneNo.isNotEmpty() && location.isNotEmpty() && imageUri != null
}

@Composable
@Preview(showBackground = true)
fun AddShoesScreenPreview() {
    WazitoECommerceTheme {
        AddShoesScreen(navController = rememberNavController())
    }
}
