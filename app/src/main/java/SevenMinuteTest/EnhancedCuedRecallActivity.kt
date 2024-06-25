package com.example.project2mobdat.SevenMinuteTest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project2mobdat.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Activity for the Enhanced Cued Recall Test in the Seven Minute Test.
 * Users are shown images and hints, and they must recall and enter names associated with the images.
 */
class EnhancedCuedRecallActivity : AppCompatActivity() {

    private lateinit var imageViews: List<ImageView>
    private lateinit var editTexts: List<EditText>
    private lateinit var hints: List<String>
    private lateinit var buttonSubmitRecall: Button
    private lateinit var textViewHint: TextView

    /**
     * Initializes the Enhanced Cued Recall Test activity.
     * Sets up imageViews, editTexts, hints, and buttonSubmitRecall.
     * Shows images to the user and enables recall submission after showing hints.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enhanced_cued_recall)

        imageViews = listOf(
            findViewById(R.id.imageView1), findViewById(R.id.imageView2), findViewById(R.id.imageView3), findViewById(R.id.imageView4),
            findViewById(R.id.imageView5), findViewById(R.id.imageView6), findViewById(R.id.imageView7), findViewById(R.id.imageView8),
            findViewById(R.id.imageView9), findViewById(R.id.imageView10), findViewById(R.id.imageView11), findViewById(R.id.imageView12),
            findViewById(R.id.imageView13), findViewById(R.id.imageView14), findViewById(R.id.imageView15), findViewById(R.id.imageView16)
        )

        editTexts = listOf(
            findViewById(R.id.editText1), findViewById(R.id.editText2), findViewById(R.id.editText3), findViewById(R.id.editText4),
            findViewById(R.id.editText5), findViewById(R.id.editText6), findViewById(R.id.editText7), findViewById(R.id.editText8),
            findViewById(R.id.editText9), findViewById(R.id.editText10), findViewById(R.id.editText11), findViewById(R.id.editText12),
            findViewById(R.id.editText13), findViewById(R.id.editText14), findViewById(R.id.editText15), findViewById(R.id.editText16)
        )

        hints = listOf(
            "They cannot fly", "They are not European", "Their calculations are not in the first place", "They all scored 500+ goals"
        )

        textViewHint = findViewById(R.id.textViewHint)
        buttonSubmitRecall = findViewById(R.id.buttonSubmitRecall)
        buttonSubmitRecall.isEnabled = false

        showImages()
        buttonSubmitRecall.setOnClickListener {
            val score = calculateRecallScore()
            CoroutineScope(Dispatchers.Main).launch {
                val intent = Intent(this@EnhancedCuedRecallActivity, ClockDrawingActivity::class.java)
                intent.putExtra("temporal_score", intent.getIntExtra("temporal_score", 0))
                intent.putExtra("recall_score", score)
                startActivity(intent)
            }
        }
    }

    /**
     * Displays images to the user with delays and clears images after delay.
     * Waits for specific intervals between groups of images.
     */
    private fun showImages() {
        CoroutineScope(Dispatchers.Main).launch {
            for (i in imageViews.indices) {
                imageViews[i].setImageResource(getImageResource(i))
                delay(2000)
                imageViews[i].setImageResource(0)
                if (i == 3 || i == 7 || i == 11) {
                    delay(5000)
                }
            }
            showHints()
            buttonSubmitRecall.isEnabled = true
        }
    }

    /**
     * Displays hints sequentially to the user.
     * Shows final instruction after all hints are shown.
     */
    private fun showHints() {
        CoroutineScope(Dispatchers.Main).launch {
            for (i in hints.indices) {
                textViewHint.text = hints[i]
                delay(10000)
            }
            textViewHint.text = "Enter the names of the images you recall:"
        }
    }

    /**
     * Calculates the user's recall score based on the entered answers.
     *
     * @return The calculated recall score based on correct answers.
     */
    private fun calculateRecallScore(): Int {
        var score = 0
        val correctAnswers = listOf("giraffe", "tiger", "lion", "elephant", "toyota", "ford", "mitsubishi", "tesla",
            "biology", "history", "literature", "physical_education", "lewandowski", "ronaldo", "messi", "suarez")
        for (i in editTexts.indices) {
            if (editTexts[i].text.toString().equals(correctAnswers[i], true)) {
                score++
            }
        }
        return score
    }

    /**
     * Retrieves the resource ID for the image based on the given index.
     *
     * @param index The index of the image.
     * @return The resource ID of the image.
     */
    private fun getImageResource(index: Int): Int {
        return when (index) {
            0 -> R.drawable.giraffe
            1 -> R.drawable.tiger
            2 -> R.drawable.lion
            3 -> R.drawable.elephant
            4 -> R.drawable.toyota
            5 -> R.drawable.ford
            6 -> R.drawable.mitsubishi
            7 -> R.drawable.tesla
            8 -> R.drawable.biology
            9 -> R.drawable.history
            10 -> R.drawable.literature
            11 -> R.drawable.physical_education
            12 -> R.drawable.lewandowski
            13 -> R.drawable.ronaldo
            14 -> R.drawable.messi
            15 -> R.drawable.suarez
            else -> 0
        }
    }
}





