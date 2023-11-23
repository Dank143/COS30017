package com.example.assignment2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailItemImage: ImageView
    private lateinit var detailItemName: TextView
    private lateinit var detailItemPrice: TextView
    private lateinit var borrowDaysSlider: Slider
    private lateinit var borrowDaysText: TextView
    private lateinit var saveButton: Button

    private lateinit var item: RentalItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Link the XML views to Kotlin objects
        with(findViewById<TextView>(R.id.detailItemName)) { detailItemName = this }
        with(findViewById<ImageView>(R.id.detailItemImage)) { detailItemImage = this }
        with(findViewById<TextView>(R.id.detailItemPrice)) { detailItemPrice = this }
        with(findViewById<Slider>(R.id.borrowDaysSlider)) { borrowDaysSlider = this }
        with(findViewById<TextView>(R.id.borrowDaysText)) { borrowDaysText = this }
        with(findViewById<Button>(R.id.saveButton)) { saveButton = this }

        item = intent.getParcelableExtra("selectedItem")!!

        populateDetails(item)

        borrowDaysSlider.addOnChangeListener { _, value, _ ->
            val days = value.toInt()
            borrowDaysText.text = "Borrow for: $days days"
            updateTotalPrice(days)
        }

        saveButton.setOnClickListener {
            val borrowedDays = borrowDaysSlider.value.toInt()

            if (borrowedDays < 1) {
                Snackbar.make(it, "Minimum borrow period is 1 day!", Snackbar.LENGTH_LONG).show()
            } else {
                val dueDate = Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_YEAR, borrowedDays)
                }.time

                item.borrowedDays = borrowedDays
                item.dueDate = dueDate

                val resultIntent = Intent().apply {
                    putExtra("updatedItem", item)
                    putExtra("borrowDays", borrowedDays)
                }

                Toast.makeText(this, "Successfully borrowed for $borrowedDays days", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Continue finding the car you want!", Toast.LENGTH_SHORT).show()
        super.onBackPressed()
    }

    private fun populateDetails(item: RentalItem) {
        detailItemName.text = item.name
        detailItemImage.setImageResource(item.imageResId)
        updateTotalPrice(borrowDaysSlider.value.toInt())
        borrowDaysText.text = "Borrow for: ${borrowDaysSlider.value.toInt()} days"
    }

    private fun updateTotalPrice(days: Int) {
        val totalPrice = days * item.pricePerDay
        detailItemPrice.text = "Price: $${totalPrice}"
    }
}
