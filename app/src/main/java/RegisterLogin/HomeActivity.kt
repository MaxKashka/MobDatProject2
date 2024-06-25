package com.example.project2mobdat.RegisterLogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project2mobdat.AD8TestActivity
import com.example.project2mobdat.R
import com.example.project2mobdat.SdmtTest.SDMTTestActivity
import com.example.project2mobdat.SevenMinuteTest.SevenMinuteTestActivity
import com.example.project2mobdat.helpers.FirebaseHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * HomeActivity is the main screen after the user logs in, showing buttons to start various tests
 * and displaying the user's test attempts and best scores.
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var button7MinuteTest: Button
    private lateinit var buttonAd8Test: Button
    private lateinit var buttonSdmtTest: Button
    private lateinit var buttonLogout: Button
    private lateinit var buttonExit: Button
    private lateinit var textView7MinuteAttempts: TextView
    private lateinit var textView7MinuteBestScore: TextView
    private lateinit var textViewAd8Result: TextView
    private lateinit var textViewSdmtAttempts: TextView
    private lateinit var textViewSdmtBestTime: TextView

    /**
     * Called when the activity is first created. Initializes the UI components and sets up click listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        button7MinuteTest = findViewById(R.id.button7MinuteTest)
        buttonAd8Test = findViewById(R.id.buttonAd8Test)
        buttonSdmtTest = findViewById(R.id.buttonSdmtTest)
        buttonLogout = findViewById(R.id.buttonLogout)
        buttonExit = findViewById(R.id.buttonExit)
        textView7MinuteAttempts = findViewById(R.id.textView7MinuteAttempts)
        textView7MinuteBestScore = findViewById(R.id.textView7MinuteBestScore)
        textViewAd8Result = findViewById(R.id.textViewAd8Result)
        textViewSdmtAttempts = findViewById(R.id.textViewSdmtAttempts)
        textViewSdmtBestTime = findViewById(R.id.textViewSdmtBestTime)

        buttonLogout.setOnClickListener {
            FirebaseHelper.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        buttonExit.setOnClickListener {
            finishAffinity()
        }

        button7MinuteTest.setOnClickListener {
            startActivity(Intent(this, SevenMinuteTestActivity::class.java))
        }

        buttonAd8Test.setOnClickListener {
            startActivity(Intent(this, AD8TestActivity::class.java))
        }

        buttonSdmtTest.setOnClickListener {
            startActivity(Intent(this, SDMTTestActivity::class.java))
        }

        loadUserData()
    }

    /**
     * Loads the user's data from the Firebase database and updates the UI with the number of attempts and best scores for each test.
     */
    private fun loadUserData() {
        CoroutineScope(Dispatchers.Main).launch {
            val userId = FirebaseHelper.getCurrentUserId() ?: return@launch
            val best7MinuteScore = withContext(Dispatchers.IO) {
                FirebaseHelper.getBestScore(userId, "7MinuteTest")
            }
            val best7MinuteAttempts = withContext(Dispatchers.IO) {
                FirebaseHelper.getAttemptCount(userId, "7MinuteTest")
            }
            val ad8Result = withContext(Dispatchers.IO) {
                FirebaseHelper.getBestScore(userId, "AD8Test")
            }
            val bestSdmtScore = withContext(Dispatchers.IO) {
                FirebaseHelper.getBestScore(userId, "SDMTTest")
            }
            val bestSdmtAttempts = withContext(Dispatchers.IO) {
                FirebaseHelper.getAttemptCount(userId, "SDMTTest")
            }

            textView7MinuteAttempts.text = "Attempts: $best7MinuteAttempts"
            textView7MinuteBestScore.text = "Best Score: $best7MinuteScore"
            textViewAd8Result.text = if (ad8Result < 2) "Result: Normal cognition" else "Result: Impairment in cognition"
            textViewSdmtAttempts.text = "Attempts: $bestSdmtAttempts"
            textViewSdmtBestTime.text = "Best Time: $bestSdmtScore"
        }
    }
}

