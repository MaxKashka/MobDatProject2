package com.example.project2mobdat.SdmtTest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project2mobdat.R
import com.example.project2mobdat.RegisterLogin.HomeActivity

/**
 * Activity to display the result of the SDMT (Symbol Digit Modalities Test).
 * It shows the score and time taken by the user, and provides a button to return to the home screen.
 */
class SDMTTestResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sdmt_test_result)

        val score = intent.getIntExtra("score", 0)
        val timeTaken = intent.getLongExtra("timeTaken", 0)

        val textViewScore: TextView = findViewById(R.id.textViewScore)
        val textViewTimeTaken: TextView = findViewById(R.id.textViewTimeTaken)
        val buttonReturnToHome: Button = findViewById(R.id.buttonReturnToHome)

        textViewScore.text = "Score: $score"
        textViewTimeTaken.text = "Time Taken: $timeTaken seconds"

        buttonReturnToHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}


