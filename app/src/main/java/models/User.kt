package com.example.project2mobdat.models

/**
 * Data class representing a user in the application.
 *
 * @property id The unique identifier of the user.
 * @property name The first name of the user.
 * @property surname The last name of the user.
 * @property email The email address of the user.
 * @property age The age of the user.
 * @property password The password of the user.
 */
data class User(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val age: String = "",
    val password: String = ""
)

