package com.example.project2mobdat.utils

import com.example.project2mobdat.models.BestScore
import com.example.project2mobdat.models.User
import com.example.project2mobdat.models.UserAttempt

/**
 * Interface for Firebase operations related to users and test attempts.
 */
interface FirebaseInterface {

    /**
     * Adds a new user to the Firestore database.
     *
     * @param user The user to add.
     * @return True if the operation was successful, false otherwise.
     */
    suspend fun addUser(user: User): Boolean

    /**
     * Retrieves the best score for a specific test type for a user.
     *
     * @param userId The ID of the user.
     * @param testType The type of the test.
     * @return The best score for the specified test type, or null if the operation failed.
     */
    suspend fun getUserBestScore(userId: String, testType: String): BestScore?

    /**
     * Saves a user's test attempt.
     *
     * @param userId The ID of the user.
     * @param attemptNumber The attempt number.
     * @param score The score achieved in the attempt.
     * @param testType The type of the test.
     * @return True if the operation was successful, false otherwise.
     */
    suspend fun saveUserAttempt(userId: String, attemptNumber: Int, score: Int, testType: String): Boolean

    /**
     * Updates the best score for a specific test type for a user.
     *
     * @param userId The ID of the user.
     * @param score The new best score.
     * @param attemptNumber The attempt number of the best score.
     * @param testType The type of the test.
     * @return True if the operation was successful, false otherwise.
     */
    suspend fun updateUserBestScore(userId: String, score: Int, attemptNumber: Int, testType: String): Boolean
}
