package com.example.project2mobdat.helpers

import com.example.project2mobdat.models.User
import com.example.project2mobdat.models.UserAttempt
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Helper object for Firebase operations including authentication and Firestore interactions.
 */
object FirebaseHelper {
    private val db = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    /**
     * Gets the current user's ID from Firebase Authentication.
     *
     * @return The user ID if a user is logged in, null otherwise.
     */
    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return True if a user is logged in, false otherwise.
     */
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    /**
     * Signs out the current user.
     */
    fun signOut() {
        auth.signOut()
    }

    /**
     * Adds a new user to the Firestore database.
     *
     * @param user The user to add.
     * @return True if the operation was successful, false otherwise.
     */
    suspend fun addUser(user: User): Boolean {
        return try {
            db.collection("users").document(user.id).set(user).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Retrieves a user from the Firestore database by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The retrieved user, or null if the operation failed.
     */
    suspend fun getUser(id: String): User? {
        return try {
            val document = db.collection("users").document(id).get().await()
            document.toObject(User::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Adds a new user test attempt to the Firestore database.
     *
     * @param attempt The user attempt to add.
     * @return True if the operation was successful, false otherwise.
     */
    suspend fun addAttempt(attempt: UserAttempt): Boolean {
        return try {
            db.collection("attempts").add(attempt).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Retrieves a list of user attempts for a specific test type.
     *
     * @param userId The ID of the user.
     * @param testType The type of the test.
     * @return A list of user attempts, or an empty list if the operation failed.
     */
    suspend fun getAttempts(userId: String, testType: String): List<UserAttempt> {
        return try {
            val result = db.collection("attempts")
                .whereEqualTo("userId", userId)
                .whereEqualTo("testType", testType)
                .get().await()
            result.toObjects(UserAttempt::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Retrieves the best score for a specific test type.
     *
     * @param userId The ID of the user.
     * @param testType The type of the test.
     * @return The best score, or 0 if the operation failed.
     */
    suspend fun getBestScore(userId: String, testType: String): Int {
        return try {
            val attempts = getAttempts(userId, testType)
            attempts.maxOfOrNull { it.score } ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Retrieves the number of attempts for a specific test type.
     *
     * @param userId The ID of the user.
     * @param testType The type of the test.
     * @return The number of attempts, or 0 if the operation failed.
     */
    suspend fun getAttemptCount(userId: String, testType: String): Int {
        return try {
            val attempts = getAttempts(userId, testType)
            attempts.size
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    /**
     * Retrieves the best time taken for a specific test type.
     *
     * @param userId The ID of the user.
     * @param testType The type of the test.
     * @return The best time taken, or 0L if the operation failed.
     */
    suspend fun getBestTime(userId: String, testType: String): Long {
        return try {
            val attempts = getAttempts(userId, testType)
            attempts.minOfOrNull { it.timeTaken } ?: 0L
        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }
    }

    /**
     * Updates the best score for a specific test type.
     *
     * @param userId The ID of the user.
     * @param score The new best score.
     * @param attemptNumber The attempt number of the best score.
     * @param testType The type of the test.
     * @return True if the operation was successful, false otherwise.
     */
    suspend fun updateUserBestScore(userId: String, score: Int, attemptNumber: Int, testType: String): Boolean {
        return try {
            val bestScore = hashMapOf(
                "score" to score,
                "attemptNumber" to attemptNumber
            )
            db.collection("bestScores").document("$userId-$testType").set(bestScore).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Adds the best score for a specific test type.
     *
     * @param userId The ID of the user.
     * @param testType The type of the test.
     * @param score The best score.
     * @return True if the operation was successful, false otherwise.
     */
    suspend fun addBestScore(userId: String, testType: String, score: Int): Boolean {
        return try {
            val bestScore = hashMapOf(
                "userId" to userId,
                "testType" to testType,
                "score" to score
            )
            db.collection("bestScores").document("$userId-$testType").set(bestScore).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}








