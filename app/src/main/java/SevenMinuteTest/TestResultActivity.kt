package com.example.project2mobdat.SevenMinuteTest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project2mobdat.R
import com.example.project2mobdat.RegisterLogin.HomeActivity
import com.example.project2mobdat.helpers.FirebaseHelper
import com.example.project2mobdat.models.UserAttempt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.ceil

/**
 * Activity to display the result of the 7-minute test and save the attempt data to Firebase.
 * Calculates the total score from temporal orientation, recall, clock drawing, and verbal fluency tests.
 */
class TestResultActivity : AppCompatActivity() {

    private lateinit var textViewTotalScore: TextView
    private lateinit var buttonReturnToHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_result)

        textViewTotalScore = findViewById(R.id.textViewTotalScore)
        buttonReturnToHome = findViewById(R.id.buttonReturnToHome)

        val temporalScore = intent.getIntExtra("temporal_score", 0)
        val recallScore = intent.getIntExtra("recall_score", 0)
        val clockScore = intent.getIntExtra("clock_score", 0)
        val verbalFluencyScore = intent.getIntExtra("verbal_fluency_score", 0)

        val totalScore = ceil((temporalScore + recallScore + clockScore + verbalFluencyScore).toDouble()).toInt()
        textViewTotalScore.text = "Total Score: $totalScore"

        val userId = FirebaseHelper.getCurrentUserId()
        if (userId != null) {
            CoroutineScope(Dispatchers.Main).launch {
                saveTestAttempt(userId, totalScore)
            }
        }

        buttonReturnToHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    /**
     * Asynchronously saves the test attempt to Firebase.
     * @param userId The ID of the current user.
     * @param score The total score of the test attempt.
     */
    private suspend fun saveTestAttempt(userId: String, score: Int) {
        val testType = "7MinuteTest"

        val attemptCount = FirebaseHelper.getAttemptCount(userId, testType) + 1

        FirebaseHelper.addAttempt(
            UserAttempt(
                userId = userId,
                testType = testType,
                attemptNumber = attemptCount,
                score = score
            )
        )

        val bestScore = FirebaseHelper.getBestScore(userId, testType)
        if (score > bestScore) {
            FirebaseHelper.addBestScore(userId, testType, score)
        }
    }
}






