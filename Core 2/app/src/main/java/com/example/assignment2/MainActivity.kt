package com.example.assignment2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var itemImage: ImageView
    private lateinit var itemName: TextView
    private lateinit var itemRating: RatingBar
    private lateinit var itemPrice: TextView
    private lateinit var nextButton: Button
    private lateinit var borrowButton: Button
    private lateinit var usedCheckBox: CheckBox
    private lateinit var brandNewCheckBox: CheckBox

    private var rentalItems: MutableList<RentalItem> = mutableListOf()
    private var currentIndex = 0

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedItem = result.data?.getParcelableExtra<RentalItem>("updatedItem")
            if (updatedItem != null) {
                rentalItems[currentIndex] = updatedItem
                displayItem()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        itemImage = findViewById(R.id.itemImage)
        itemName = findViewById(R.id.itemName)
        itemRating = findViewById(R.id.itemRating)
        itemPrice = findViewById(R.id.itemPrice)
        nextButton = findViewById(R.id.nextButton)
        borrowButton = findViewById(R.id.borrowButton)

        // Initialize checkboxes
        usedCheckBox = findViewById(R.id.usedCheckBox)
        brandNewCheckBox = findViewById(R.id.brandNewCheckBox)

        // Checkbox listeners
        usedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                brandNewCheckBox.isChecked = false
            }
        }

        brandNewCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                usedCheckBox.isChecked = false
            }
        }

        // Populate and display items
        populateItems()
        displayItem()

        // Button click listeners
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % rentalItems.size
            displayItem()
        }

        borrowButton.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("selectedItem", rentalItems[currentIndex])
            startForResult.launch(intent)
        }

        itemImage.setOnClickListener {
            val intent = Intent(this, ItemDetailActivity::class.java)
            intent.putExtra("selectedItem", rentalItems[currentIndex])
            startActivity(intent)
        }
    }

    private fun populateItems() {
        rentalItems = mutableListOf(
            RentalItem("Car 1 - An Italian beauty", 3f, "Choice 1", 1000.0, "325km/h", "V10", "1487kg", "Lamborghini", R.drawable.car1),
            RentalItem("Car 2 - The real horse power", 4f, "Choice 2", 1250.0, "372km/h", "V12", "1480kg", "Ferrari", R.drawable.car2),
            RentalItem("Car 3 - Big, heavy hitter", 5f, "Choice 3", 1500.0, "431km/h", "W16", "1888kg", "Bugatti", R.drawable.car3)
        )
    }

    private fun displayItem() {
        val currentItem = rentalItems[currentIndex]
        with(currentItem) {
            itemName.text = name
            itemRating.rating = rating
            itemPrice.text = "Price: $${pricePerDay}"
            itemImage.setImageResource(imageResId)

            val itemDueDate: TextView = findViewById(R.id.DueDate)
            if (dueDate != null) {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dueDateString = sdf.format(dueDate)
                Log.d("Main Activity", "Due date: $dueDate")
                itemDueDate.text = "Due on: $dueDateString"
            } else {
                itemDueDate.text = "Not borrowed"
            }
        }
    }
}

