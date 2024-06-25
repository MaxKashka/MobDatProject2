package com.example.project2mobdat.RegisterLogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project2mobdat.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

/**
 * Activity for user login functionality.
 */
class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonChangeToRegister: Button

    /**
     * Called when the activity is first created. Initializes the UI components and sets up click listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        initViews()
        setupListeners()
    }

    /**
     * Initializes the view components.
     */
    private fun initViews() {
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonChangeToRegister = findViewById(R.id.buttonChangeToRegister)
    }

    /**
     * Sets up click listeners for the buttons.
     */
    private fun setupListeners() {
        buttonLogin.setOnClickListener {
            loginUser(editTextEmail.text.toString().trim(), editTextPassword.text.toString().trim())
        }

        buttonChangeToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    /**
     * Attempts to log in the user with the provided email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     */
    private fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast("Please enter both email and password")
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email address")
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                withContext(Dispatchers.Main) {
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToast("Login failed: ${e.localizedMessage}")
                }
            }
        }
    }

    /**
     * Displays a toast message to the user.
     *
     * @param message The message to be displayed.
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}




