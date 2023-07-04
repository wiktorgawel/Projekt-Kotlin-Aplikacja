package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private val words = mutableListOf("onion", "cabbage", "pineapple", "cherry", "sweetcorn")
    private val translations = mutableMapOf(
        "onion" to "cebula",
        "cabbage" to "kapusta",
        "pineapple" to "ananas",
        "cherry" to "wiśnia",
        "sweetcorn" to "kukurydza"
    )
    private var score = 0

    private lateinit var textViewWord: TextView
    private lateinit var textViewScore: TextView
    private lateinit var editTextTranslation: EditText
    private lateinit var buttonCheck: Button
    private lateinit var textViewResult: TextView
    private lateinit var buttonKnown: Button
    private lateinit var buttonUnknown: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewWord = findViewById(R.id.textViewWord)
        textViewScore = findViewById(R.id.textViewScore)
        editTextTranslation = findViewById(R.id.editTextTranslation)
        buttonCheck = findViewById(R.id.buttonCheck)
        textViewResult = findViewById(R.id.textViewResult)
        buttonKnown = findViewById(R.id.buttonKnown)
        buttonUnknown = findViewById(R.id.buttonUnknown)

        updateUI()

        buttonCheck.setOnClickListener {
            checkTranslation()
        }

        buttonKnown.setOnClickListener {
            handleKnownOption()
        }

        buttonUnknown.setOnClickListener {
            handleUnknownOption()
        }
    }

    private fun updateUI() {
        if (words.isNotEmpty()) {
            val randomIndex = Random().nextInt(words.size)
            val currentWord = words[randomIndex]
            textViewWord.text = "Aktualne słowo: $currentWord"
            textViewScore.text = "Punkty: $score"
        } else {
            textViewWord.text = "Koniec gry. Twój końcowy wynik: $score"
            textViewScore.text = ""
            editTextTranslation.isEnabled = false
            buttonCheck.isEnabled = false
            textViewResult.text = ""
            buttonKnown.isEnabled = false
            buttonUnknown.isEnabled = false
        }
        editTextTranslation.text.clear()
    }

    private fun checkTranslation() {
        val currentWord = textViewWord.text.toString().substringAfter(": ")
        val translation = editTextTranslation.text.toString().trim()

        val correctTranslation = translations[currentWord]
        if (translation.equals(correctTranslation, ignoreCase = true)) {
            score += 10
        } else {
            score -= 5
            words.remove(currentWord)
        }

        updateUI()
    }


    private fun handleKnownOption() {
        val currentWord = textViewWord.text.toString().substringAfter(": ")
        score += 10
        words.remove(currentWord)
        translations.remove(currentWord)
        updateUI()
    }

    private fun handleUnknownOption() {
        score -= 5
        updateUI()
    }
}
