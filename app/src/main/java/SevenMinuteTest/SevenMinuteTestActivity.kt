package com.example.project2mobdat.SevenMinuteTest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.project2mobdat.R
import com.example.project2mobdat.TemporalOrientationActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Activity for starting the Seven Minute Test.
 * Users can initiate the test by clicking a button.
 * Tracks the current score, attempt number, and maximum achievable score.
 */
class SevenMinuteTestActivity : AppCompatActivity() {

    private lateinit var buttonStartTest: Button
    private var currentScore: Int = 0
    private var attemptNumber: Int = 0
    private val maxScore: Int = 161

    /**
     * Initializes the Seven Minute Test activity.
     * Sets up the buttonStartTest and listens for click events to start the test.
     * Initiates the TemporalOrientationActivity to begin the test session.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seven_minute_test)

        buttonStartTest = findViewById(R.id.buttonStartTest)
        buttonStartTest.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                currentScore = 0
                attemptNumber++

                val intent = Intent(this@SevenMinuteTestActivity, TemporalOrientationActivity::class.java)
                intent.putExtra("currentScore", currentScore)
                startActivity(intent)
                finish()
            }
        }
    }
}


