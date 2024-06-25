package com.example.project2mobdat.SevenMinuteTest

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
import java.util.*

/**
 * Activity for the Verbal Fluency test.
 * This test requires the user to list as many different animals as possible within one minute.
 */
class VerbalFluencyActivity : AppCompatActivity() {

    private lateinit var editTextAnimal: EditText
    private lateinit var textViewAnimals: TextView
    private lateinit var buttonSubmitAnimal: Button
    private var score = 0.0
    private val animalSet = mutableSetOf<String>()
    private val animals = setOf(
        "lion", "tiger", "elephant", "giraffe", "zebra", "monkey", "bear", "panda", "koala", "kangaroo",
        "leopard", "cheetah", "jaguar", "hyena", "rhinoceros", "hippopotamus", "crocodile", "alligator",
        "ostrich", "peacock", "flamingo", "pelican", "parrot", "sparrow", "eagle", "hawk", "falcon",
        "vulture", "penguin", "seal", "walrus", "otter", "dolphin", "whale", "shark", "octopus", "squid",
        "crab", "lobster", "jellyfish", "starfish", "seahorse", "clownfish", "angelfish", "guppy", "goldfish",
        "tuna", "salmon", "trout", "bass", "catfish", "carp", "pike", "swordfish", "marlin", "stingray",
        "barracuda", "eel", "manta ray", "moray eel", "parrotfish", "pufferfish", "seabass", "cod", "herring",
        "anchovy", "sardine", "mackerel", "mollusk", "shrimp", "plankton", "krill", "coral", "sponge",
        "barnacle", "clam", "scallop", "oyster", "mussel", "snail", "slug", "worm", "ant", "bee", "wasp",
        "hornet", "butterfly", "moth", "beetle", "ladybug", "grasshopper", "cricket", "locust", "dragonfly",
        "damselfly", "fly", "mosquito", "gnat", "flea", "tick", "spider", "scorpion", "tarantula", "centipede",
        "millipede", "cockroach", "termite", "weevil", "earwig", "silverfish", "firefly", "glowworm"
    )

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied. Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verbal_fluency)

        editTextAnimal = findViewById(R.id.editTextAnimal)
        textViewAnimals = findViewById(R.id.textViewAnimals)
        buttonSubmitAnimal = findViewById(R.id.buttonSubmitAnimal)

        buttonSubmitAnimal.setOnClickListener {
            val animal = editTextAnimal.text.toString().trim().lowercase(Locale.getDefault())
            if (animal in animals && animal !in animalSet) {
                animalSet.add(animal)
                score += 1.5
                textViewAnimals.append("$animal\n")
                editTextAnimal.text.clear()
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(60000)
            val finalScore = score
            val intent = Intent(this@VerbalFluencyActivity, TestResultActivity::class.java).apply {
                putExtra("temporal_score", intent.getIntExtra("temporal_score", 0))
                putExtra("recall_score", intent.getIntExtra("recall_score", 0))
                putExtra("clock_score", intent.getIntExtra("clock_score", 0))
                putExtra("verbal_score", finalScore)
            }
            startActivity(intent)
        }
    }
}




