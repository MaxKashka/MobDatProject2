package com.example.project2mobdat.models

/**
 * Data class representing the best score for a specific test.
 *
 * @property testType The type of the test.
 * @property bestScore The highest score achieved by the user.
 * @property attemptNumber The attempt number in which the best score was achieved.
 */
data class BestScore(
    val testType: String = "",
    val bestScore: Int = 0,
    val attemptNumber: Int = 0
)
