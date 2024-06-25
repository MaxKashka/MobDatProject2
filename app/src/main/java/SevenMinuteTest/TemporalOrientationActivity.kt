package com.example.project2mobdat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project2mobdat.SevenMinuteTest.EnhancedCuedRecallActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

/**
 * Activity for testing temporal orientation by comparing user-entered date and time with current date and time.
 * Calculates a score based on correctness of year, month, date, day, and time entered.
 */
class TemporalOrientationActivity : AppCompatActivity() {

    private lateinit var currentDateTime: Date
    private lateinit var correctYear: String
    private lateinit var correctMonth: String
    private lateinit var correctDate: String
    private lateinit var correctDay: String
    private lateinit var correctTime: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temporal_orientation)

        currentDateTime = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm", Locale.getDefault())
        val dateComponents = dateFormat.format(currentDateTime).split("-")
        correctYear = dateComponents[0]
        correctMonth = dateComponents[1]
        correctDate = dateComponents[2]
        correctDay = SimpleDateFormat("EEEE", Locale.getDefault()).format(currentDateTime)
        correctTime = dateComponents[3] + ":" + dateComponents[4]

        findViewById<TextView>(R.id.textViewCurrentTime).text = "Enter the current time:"

        findViewById<Button>(R.id.buttonSubmitTemporal).setOnClickListener {
            val userYear = findViewById<EditText>(R.id.editTextYear).text.toString()
            val userMonth = findViewById<EditText>(R.id.editTextMonth).text.toString()
            val userDate = findViewById<EditText>(R.id.editTextDate).text.toString()
            val userDay = findViewById<EditText>(R.id.editTextDay).text.toString()
            val userTime = findViewById<EditText>(R.id.editTextTime).text.toString()

            val score = calculateTemporalOrientationScore(
                userYear, userMonth, userDate, userDay, userTime
            )

            CoroutineScope(Dispatchers.Main).launch {
                val intent = Intent(this@TemporalOrientationActivity, EnhancedCuedRecallActivity::class.java)
                intent.putExtra("temporal_score", score)
                startActivity(intent)
            }
        }
    }

    /**
     * Calculates the score for temporal orientation based on user-entered data.
     * @param year User-entered year.
     * @param month User-entered month.
     * @param date User-entered date.
     * @param day User-entered day.
     * @param time User-entered time.
     * @return The calculated score based on correctness of user-entered data.
     */
    private fun calculateTemporalOrientationScore(year: String, month: String, date: String, day: String, time: String): Int {
        var score = 113

        if (year != correctYear) score -= 10
        if (month != correctMonth) score -= 5
        if (date != correctDate) score -= 1
        if (day != correctDay) score -= 1

        val correctTimeComponents = correctTime.split(":")
        val userTimeComponents = time.split(":")
        if (correctTimeComponents.size == 2 && userTimeComponents.size == 2) {
            val correctHour = correctTimeComponents[0].toInt()
            val correctMinute = correctTimeComponents[1].toInt()
            val userHour = userTimeComponents[0].toInt()
            val userMinute = userTimeComponents[1].toInt()

            if (correctHour != userHour || (correctMinute - userMinute).absoluteValue > 30) {
                score -= 1
            }
        }

        return score
    }
}


