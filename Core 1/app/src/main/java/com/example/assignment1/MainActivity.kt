package com.example.assignment1

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity // Import AppCompatActivity for compatibility
import android.widget.Button
import android.widget.TextView
import android.util.Log
import kotlin.random.Random

class DiceRollerActivity : AppCompatActivity() { // Use AppCompatActivity for compatibility
    private var currentValue = 0
    private var diceRoll = 0
    private var isFirstRoll = true // Flag to track the first roll

    private lateinit var rollButton: Button
    private lateinit var addButton: Button
    private lateinit var subButton: Button
    private lateinit var resetButton: Button

    private fun isVietLocale(): Boolean {
        return resources.configuration.locales[0].language == "vn"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        rollButton = findViewById(R.id.button2)
        addButton = findViewById(R.id.button3)
        subButton = findViewById(R.id.button4)
        resetButton = findViewById(R.id.button5)

        // Set onClick listeners for buttons
        rollButton.setOnClickListener {
            rollDice()
            setButtonStates()
        }

        addButton.setOnClickListener {
            addValue()
            setButtonStates()
        }

        subButton.setOnClickListener {
            subtractValue()
            setButtonStates()
        }

        resetButton.setOnClickListener {
            resetValue()
        }

        // Restore saved state AFTER setting the onClick listeners
        if (savedInstanceState != null) {
            currentValue = savedInstanceState.getInt("currentValue")
            diceRoll = savedInstanceState.getInt("diceRoll", 0)
            isFirstRoll = savedInstanceState.getBoolean("isFirstRoll", true) // Restore first roll flag
            rollButton.isEnabled = savedInstanceState.getBoolean("isRollButtonEnabled", true)
            addButton.isEnabled = savedInstanceState.getBoolean("isAddButtonEnabled", false)
            subButton.isEnabled = savedInstanceState.getBoolean("isSubButtonEnabled", false)
        } else {
            setButtonStates()
        }

        // Initial display (display current value only)
        updateDisplay()

        // Log a message to indicate that onCreate() is called
        Log.d("DiceRollerActivity", "onCreate() is called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the current state
        outState.putInt("currentValue", currentValue)
        outState.putInt("diceRoll", diceRoll)
        outState.putBoolean("isRollButtonEnabled", rollButton.isEnabled)
        outState.putBoolean("isAddButtonEnabled", addButton.isEnabled)
        outState.putBoolean("isSubButtonEnabled", subButton.isEnabled)
        outState.putBoolean("isFirstRoll", isFirstRoll) // Save first roll flag

        // Log a message to indicate that onSaveInstanceState() is called
        Log.d("DiceRollerActivity", "onSaveInstanceState() is called")
    }

    private val dice = Dice(6, 1)

    private fun rollDice() {
        // Roll the dice and update the display
        diceRoll = dice.roll()
        val diceUnicode = dice.getUnicodeForRoll(diceRoll)
        updateDisplay(diceUnicode)
        setButtonStates()
        isFirstRoll = false // Set first roll flag to false after the first roll

        // Log the result of the dice roll
        Log.d("DiceRollerActivity", "Dice rolled: $diceRoll")
    }

    private fun addValue() {
        // Add the rolled value to the current value
        currentValue += diceRoll
        diceRoll = 0
        updateDisplay()
        setButtonStates()

        // Log a message to indicate that a value is added
        Log.d("DiceRollerActivity", "Value added: $currentValue")
    }

    private fun subtractValue() {
        // Subtract the rolled value from the current value (minimum value is 0)
        currentValue = maxOf(0, currentValue - diceRoll)
        diceRoll = 0
        updateDisplay()
        setButtonStates()

        // Log a message to indicate that a value is subtracted
        Log.d("DiceRollerActivity", "Value subtracted: $currentValue")
    }

    private fun resetValue() {
        // Reset both current and dice values to 0
        currentValue = 0
        diceRoll = 0
        updateDisplay()
        setButtonStates()

        // Log a message to indicate that values are reset
        Log.d("DiceRollerActivity", "Values reset")
    }

    private fun setButtonStates() {
        // Enable/disable buttons based on the dice roll state
        if (diceRoll == 0) {
            rollButton.isEnabled = true
            addButton.isEnabled = false
            subButton.isEnabled = false
        } else {
            rollButton.isEnabled = false
            addButton.isEnabled = true
            subButton.isEnabled = true
        }
    }

    private fun updateDisplay(diceUnicode: String = "") {
        // Update the result display with the dice value and current value
        val resultTextView: TextView = findViewById(R.id.textView)
        val displayValue = "$diceUnicode\n$currentValue"
        resultTextView.text = displayValue
        when {
            currentValue < 19 -> resultTextView.setTextColor(Color.BLACK)
            currentValue > 20 -> resultTextView.setTextColor(Color.RED)
            currentValue == 20 -> resultTextView.setTextColor(Color.GREEN)
        }
    }
}

class Dice(val numSides: Int, val seed: Long? = null) {
    private val random = seed?.let { Random(it) } ?: Random

    fun roll(): Int {
        // Roll the dice and return the result
        return random.nextInt(1, numSides + 1)
    }

    fun getUnicodeForRoll(rollValue: Int): String {
        // Get the Unicode representation of the dice roll
        val unicodeDiceFaces = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")
        return unicodeDiceFaces[rollValue - 1]
    }
}



