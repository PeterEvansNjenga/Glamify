@file:Suppress("DEPRECATION")

package com.example.glamify.models

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.glamify.navigation.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel(private val navController: NavHostController, private val context: Context) {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val progress: ProgressDialog = ProgressDialog(context)
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    init {
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")
    }

    @SuppressLint("RestrictedApi")
    fun signup(name: String, email: String, password: String) {
        progress.show()
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            val userId = mAuth.currentUser!!.uid
            val userProfile = com.example.glamify.data.User(name, email, password, userId)

            // Save user ID to shared preferences to indicate successful sign-up
            sharedPreferences.edit().putBoolean(KEY_FIRST_TIME_USER, false).apply()

            // Create a reference table called Users inside of the Firebase database
            val usersRef = FirebaseDatabase.getInstance().getReference()
                .child("Users_s/$userId")
            usersRef.setValue(userProfile).addOnCompleteListener {
                progress.dismiss()
                if (it.isSuccessful) {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    navController.navigate(VIEW_USER_SHOE)
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun login(email: String, password: String) {
        progress.show()
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            progress.dismiss()
            if (it.isSuccessful) {
                // Save user ID to shared preferences to indicate successful login
                sharedPreferences.edit().putBoolean(KEY_FIRST_TIME_USER, false).apply()

                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                navController.navigate(VIEW_USER_SHOE)
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun logout() {
        mAuth.signOut()
        // Clear the user ID from shared preferences on logout
        sharedPreferences.edit().putBoolean(KEY_FIRST_TIME_USER, true).apply()
        navController.navigate(LOGIN_URL)
    }

    fun isLoggedIn(): Boolean = mAuth.currentUser != null

    fun isFirstTimeUser(): Boolean = sharedPreferences.getBoolean(KEY_FIRST_TIME_USER, true)

    companion object {
        const val SHARED_PREF_NAME = "com.example.glamify.SHARED_PREF"
        const val KEY_FIRST_TIME_USER = "FIRST_TIME_USER"
    }
}
