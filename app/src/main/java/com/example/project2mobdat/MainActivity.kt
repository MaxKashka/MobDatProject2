package com.example.project2mobdat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project2mobdat.RegisterLogin.HomeActivity
import com.example.project2mobdat.RegisterLogin.LoginActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * MainActivity checks the authentication status of the current user and redirects
 * to either the HomeActivity or LoginActivity based on the authentication state.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    /**
     * Initializes the activity, checks the current authentication status,
     * and redirects to the appropriate activity.
     *
     * If the user is authenticated (not null), navigates to HomeActivity.
     * Otherwise, navigates to LoginActivity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Authentication instance
        auth = FirebaseAuth.getInstance()

        // Check the current user's authentication status
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // If user is authenticated, navigate to HomeActivity
            startActivity(Intent(this, HomeActivity::class.java))
            finish() // Close MainActivity to prevent going back to it
        } else {
            // If user is not authenticated, navigate to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Close MainActivity to prevent going back to it
        }
    }
}

