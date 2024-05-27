@file:Suppress("DEPRECATION")

package com.example.glamify.models

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.example.glamify.data.Product
import com.example.glamify.navigation.LOGIN_URL
import com.example.glamify.navigation.VIEW_SHOES_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class ProductViewModel(private var navController: NavHostController, private var context: Context) {
    private var authViewModel: AuthViewModel = AuthViewModel(navController, context)
    private var progress: ProgressDialog = ProgressDialog(context).apply {
        setTitle("Loading")
        setMessage("Please wait...")
    }

    init {
        if (!authViewModel.isLoggedIn()) {
            navController.navigate(LOGIN_URL)
        }
    }

    private fun getDatabaseReference(path: String): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference(path)
    }

    private fun getCurrentUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    fun uploadProduct(
        name: String,
        description: String,
        price: String,
        phoneNo: String,
        location: String,
        filePath: Uri
    ) {
        val productId = System.currentTimeMillis().toString()
        val storageRef = FirebaseStorage.getInstance().reference.child("Shoes_Products/$productId")

        progress.show()
        storageRef.putFile(filePath).addOnCompleteListener { task ->
            progress.dismiss()
            if (task.isSuccessful) {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    val userId = getCurrentUserId()

                    val product = Product(name, description, price, phoneNo, location, imageUrl, productId, userId ?: "")
                    val databaseRef = getDatabaseReference("Shoes_Products/$productId")
                    databaseRef.setValue(product).addOnCompleteListener { dbTask ->
                        if (dbTask.isSuccessful) {
                            navController.navigate(VIEW_SHOES_URL)
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Upload error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun allProducts(
        product: MutableState<Product>,
        products: SnapshotStateList<Product>
    ): SnapshotStateList<Product> {
        progress.show()
        val ref = getDatabaseReference("Shoes_Products")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()
                for (snap in snapshot.children) {
                    val retrievedProduct = snap.getValue(Product::class.java)
                    if (retrievedProduct != null) {
                        product.value = retrievedProduct
                        products.add(retrievedProduct)
                    }
                }
                progress.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                progress.dismiss()
                Toast.makeText(context, "DB error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
        return products
    }

    fun deleteProduct(productId: String) {
        val ref = getDatabaseReference("Shoes_Products/$productId")
        ref.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Deletion Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
