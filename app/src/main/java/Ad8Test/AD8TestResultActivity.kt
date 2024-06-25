package com.example.project2mobdat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project2mobdat.RegisterLogin.HomeActivity

/**
 * Activity class for displaying the result of the AD8 test.
 */
class AD8TestResultActivity : AppCompatActivity() {

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied in [onSaveInstanceState].
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad8_test_result)

        val textViewResult: TextView = findViewById(R.id.textViewResult)
        val buttonReturnToHome: Button = findViewById(R.id.buttonReturnToHome)

        val result = intent.getStringExtra("result")
        textViewResult.text = result

        buttonReturnToHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}


