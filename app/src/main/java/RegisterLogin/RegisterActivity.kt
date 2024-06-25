package com.example.project2mobdat.RegisterLogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project2mobdat.R
import com.example.project2mobdat.helpers.FirebaseHelper
import com.example.project2mobdat.models.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

/**
 * Activity for user registration.
 */
class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var editTextEmail: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextSurname: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextRepeatPassword: EditText
    private lateinit var buttonSignUp: Button
    private lateinit var buttonChangeToLogin: Button

    /**
     * Initializes views and Firebase Authentication instance.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        initViews()

        buttonSignUp.setOnClickListener {
            attemptRegistration()
        }

        buttonChangeToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    /**
     * Initializes EditText and Button views.
     */
    private fun initViews() {
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextName = findViewById(R.id.editTextName)
        editTextSurname = findViewById(R.id.editTextSurname)
        editTextAge = findViewById(R.id.editTextAge)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextRepeatPassword = findViewById(R.id.editTextRepeatPassword)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        buttonChangeToLogin = findViewById(R.id.buttonChangeToLogin)
    }

    /**
     * Attempts to register a user using the provided information.
     * Validates input fields and checks password requirements.
     */
    private fun attemptRegistration() {
        val email = editTextEmail.text.toString().trim()
        val name = editTextName.text.toString().trim()
        val surname = editTextSurname.text.toString().trim()
        val age = editTextAge.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val repeatPassword = editTextRepeatPassword.text.toString().trim()

        if (email.isEmpty() || name.isEmpty() || surname.isEmpty() || age.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            showToast("Please fill in all fields")
            return
        }

        if (password != repeatPassword) {
            showToast("Passwords do not match")
            return
        }

        if (validatePassword(password)) {
            registerUser(email, password, name, surname, age)
        } else {
            showToast("Password must be at least 5 characters long and contain special characters: !, @, #, $, %, ^, &, *")
        }
    }

    /**
     * Registers a new user with Firebase Authentication.
     * Adds the user to Firebase Realtime Database upon successful registration.
     *
     * @param email User's email address.
     * @param password User's password.
     * @param name User's first name.
     * @param surname User's last name.
     * @param age User's age.
     */
    private fun registerUser(email: String, password: String, name: String, surname: String, age: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val firebaseUid = auth.currentUser?.uid ?: return@addOnCompleteListener

                CoroutineScope(Dispatchers.IO).launch {
                    val user = User(firebaseUid, name, surname, email, age, password)
                    val result = FirebaseHelper.addUser(user)

                    withContext(Dispatchers.Main) {
                        if (result) {
                            showToast("User registered successfully")
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        } else {
                            showToast("Failed to save user to database")
                        }
                    }
                }
            } else {
                showToast("Registration failed: ${task.exception?.message}")
            }
        }
    }

    /**
     * Validates the password against specified criteria.
     *
     * @param password Password to validate.
     * @return `true` if the password meets the criteria, `false` otherwise.
     */
    private fun validatePassword(password: String): Boolean {
        val specialCharacters = setOf('!', '@', '#', '$', '%', '^', '&', '*')
        return password.length >= 5 && password.any { it in specialCharacters }
    }

    /**
     * Displays a short toast message on the screen.
     *
     * @param message Message to display.
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

