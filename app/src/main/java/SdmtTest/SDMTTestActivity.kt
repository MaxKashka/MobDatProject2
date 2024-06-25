package com.example.project2mobdat.SdmtTest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project2mobdat.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Activity for conducting Symbol Digit Modalities Test (SDMT).
 * Users are presented with symbol sequences and must input corresponding numbers based on a predefined mapping.
 */
class SDMTTestActivity : AppCompatActivity() {

    private val symbolsMap = mapOf(
        '^' to 1, '$' to 2, '#' to 3, '@' to 4, '*' to 5,
        '!' to 6, '%' to 7, '&' to 8, '~' to 9, '+' to 0
    )

    private lateinit var textViewSymbols: TextView
    private lateinit var editTextNumber: EditText
    private lateinit var buttonSubmit: Button
    private lateinit var textViewResult: TextView

    private var currentVariant = ""
    private var correctNumbers = ""
    private var currentIndex = 0
    private var mistakes = 0

    private val variants = listOf(
        "^$#@^*^$^#@^*^%$%#%...",
        "!@#^*!@#^*!@#^*%!@#...",
        "$@^%$@^%*$@^%$@^%*...",
        "@#^$@#^$@#^$@^%*@#...",
        "^$#@^*^$^#@^*^%$%#%..."
    )

    /**
     * Initializes views and starts the SDMT test.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sdmt_test)

        textViewSymbols = findViewById(R.id.textViewSymbols)
        editTextNumber = findViewById(R.id.editTextNumber)
        buttonSubmit = findViewById(R.id.buttonSubmit)
        textViewResult = findViewById(R.id.textViewResult)

        startTest()

        buttonSubmit.setOnClickListener {
            checkAnswer()
        }
    }

    /**
     * Starts the SDMT test by selecting a random variant and displaying the symbol mapping.
     * Delays the display of symbols to allow the user to prepare.
     */
    private fun startTest() {
        currentVariant = variants.random()

        textViewSymbols.text = "Symbols: ${symbolsMap.entries.joinToString { "${it.key} -> ${it.value}" }}"

        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            showNextSymbol()
        }
    }

    /**
     * Displays the next symbol in the current variant sequence.
     * If all symbols have been displayed, ends the test.
     */
    private fun showNextSymbol() {
        if (currentIndex < currentVariant.length) {
            textViewResult.text = currentVariant[currentIndex].toString()
        } else {
            endTest()
        }
    }

    /**
     * Checks the user's answer against the correct answer for the current symbol.
     * Updates the score and displays feedback based on the correctness of the answer.
     */
    private fun checkAnswer() {
        val userAnswer = editTextNumber.text.toString()
        val correctAnswer = symbolsMap[currentVariant[currentIndex]]?.toString()

        if (userAnswer == correctAnswer) {
            correctNumbers += userAnswer
        } else {
            mistakes++
            textViewResult.text = "Mistake, please be more attentive"
        }

        currentIndex++
        editTextNumber.text.clear()
        showNextSymbol()
    }

    /**
     * Ends the SDMT test and navigates to the result activity.
     * Calculates the score based on correct answers and mistakes.
     */
    private fun endTest() {
        val score = correctNumbers.length - mistakes
        val intent = Intent(this, SDMTTestResultActivity::class.java)
        intent.putExtra("sdmt_score", score)
        startActivity(intent)
    }
}




