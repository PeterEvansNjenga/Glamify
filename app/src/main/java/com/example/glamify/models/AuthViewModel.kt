@file:Suppress("DEPRECATION")

package com.example.glamify.models

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.glamify.data.User
import com.example.glamify.navigation.LOGIN_URL
import com.example.glamify.navigation.SPLASH_URL
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
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = mAuth.currentUser!!.uid
                val userProfile = User(name, email, password, userId)

                // Save user ID to shared preferences to indicate successful sign-up
                sharedPreferences.edit().putBoolean(KEY_FIRST_TIME_USER, false).apply()

                // Save user profile to Firebase database
                val usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId)
                usersRef.setValue(userProfile).addOnCompleteListener { innerTask ->
                    progress.dismiss()
                    if (innerTask.isSuccessful) {
                        Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show()
                        navController.navigate(SPLASH_URL)
                    } else {
                        Toast.makeText(context, "Error signing up", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                progress.dismiss()
                Toast.makeText(context, "Error creating account", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(email: String, password: String) {
        progress.show()
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            progress.dismiss()
            if (task.isSuccessful) {
                // Save user ID to shared preferences to indicate successful login
                sharedPreferences.edit().putBoolean(KEY_FIRST_TIME_USER, false).apply()

                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                navController.navigate(SPLASH_URL)
            } else {
                Toast.makeText(context, "Error logging in", Toast.LENGTH_SHORT).show()
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
