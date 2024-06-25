package com.example.project2mobdat.SevenMinuteTest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.project2mobdat.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Activity for the Clock Drawing Test in the Seven Minute Test.
 * Users set the time on a TimePicker and score based on accuracy compared to a target time.
 */
class ClockDrawingActivity : AppCompatActivity() {

    private lateinit var timePicker: TimePicker

    /**
     * Sets up the Clock Drawing Test activity.
     * Displays the TimePicker after a delay and calculates the score based on user's input time.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock_drawing)

        timePicker = findViewById(R.id.timePicker)
        timePicker.setIs24HourView(true)

        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            startClockDrawing()
        }

        findViewById<Button>(R.id.buttonSubmitTime).setOnClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute

            val clockScore = calculateClockScore(hour, minute)

            val recallScore = intent.getIntExtra("recall_score", 0)
            val totalScore = intent.getIntExtra("temporal_score", 0) + recallScore + clockScore

            val intent = Intent(this@ClockDrawingActivity, TestResultActivity::class.java)
            intent.putExtra("total_score", totalScore)
            startActivity(intent)
        }
    }

    /**
     * Displays the TimePicker view for the user to set the time.
     */
    private fun startClockDrawing() {
        timePicker.visibility = View.VISIBLE
    }

    /**
     * Calculates the score based on the user's input hour and minute compared to a target time.
     *
     * @param hour User's selected hour on the TimePicker.
     * @param minute User's selected minute on the TimePicker.
     * @return Score calculated based on the accuracy of the user's input time.
     */
    private fun calculateClockScore(hour: Int, minute: Int): Int {
        val correctHour = 11
        val correctMinute = 15
        var score = 7

        if (hour != correctHour) {
            score -= 2
        }
        if (minute != correctMinute) {
            score -= 2
        }
        else 3

        return score
    }
}



