package com.example.project2mobdat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project2mobdat.helpers.FirebaseHelper
import com.example.project2mobdat.models.UserAttempt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Activity class for conducting the AD8 test.
 */
class AD8TestActivity : AppCompatActivity() {

    private lateinit var radioGroups: List<RadioGroup>
    private lateinit var buttonSubmitAd8: Button

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied in [onSaveInstanceState].
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad8_test)

        radioGroups = listOf(
            findViewById(R.id.radioGroup1), findViewById(R.id.radioGroup2), findViewById(R.id.radioGroup3), findViewById(R.id.radioGroup4),
            findViewById(R.id.radioGroup5), findViewById(R.id.radioGroup6), findViewById(R.id.radioGroup7), findViewById(R.id.radioGroup8)
        )

        buttonSubmitAd8 = findViewById(R.id.buttonSubmitAd8)

        buttonSubmitAd8.setOnClickListener {
            val score = calculateAd8Score()
            saveResult(score)
        }
    }

    /**
     * Calculates the AD8 score based on the user's responses.
     *
     * @return The total AD8 score.
     */
    private fun calculateAd8Score(): Int {
        var score = 0
        for (group in radioGroups) {
            val selectedRadioButtonId = group.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                if (selectedRadioButton.text == "Yes") {
                    score++
                }
            }
        }
        return score
    }

    /**
     * Saves the result of the AD8 test to the database and navigates to the result activity.
     *
     * @param score The score obtained from the AD8 test.
     */
    private fun saveResult(score: Int) {
        val result = if (score < 2) "Normal cognition" else "Impairment in cognition"
        val userId = FirebaseHelper.getCurrentUserId()
        if (userId != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val attempt = UserAttempt(
                    userId = userId,
                    testType = "AD8",
                    attemptNumber = 1,
                    score = score,
                    timeTaken = 0L
                )
                val success = FirebaseHelper.addAttempt(attempt)
                withContext(Dispatchers.Main) {
                    if (success) {
                        val intent = Intent(this@AD8TestActivity, AD8TestResultActivity::class.java).apply {
                            putExtra("test_type", "AD8")
                            putExtra("result", result)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@AD8TestActivity, "Failed to save the result", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }
}



