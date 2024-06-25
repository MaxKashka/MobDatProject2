package com.example.project2mobdat.models

/**
 * Data class representing a user's attempt at a test.
 *
 * @property userId The unique identifier of the user.
 * @property testType The type of test the user attempted.
 * @property attemptNumber The number of this attempt (incremental).
 * @property score The score the user achieved on this attempt.
 * @property timeTaken The time taken to complete the test, in milliseconds.
 */
data class UserAttempt(
    val userId: String = "",
    val testType: String = "",
    val attemptNumber: Int = 0,
    val score: Int = 0,
    val timeTaken: Long = 0L
)


