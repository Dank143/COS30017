package com.example.assignment2

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ItemDetailActivity : AppCompatActivity() {

    private lateinit var detailItemImage: ImageView
    private lateinit var detailItemName: TextView
    private lateinit var detailTopSpeed: TextView
    private lateinit var detailEngine: TextView
    private lateinit var detailWeight: TextView
    private lateinit var detailManufacturer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        // Initialize views with the same variable names
        detailItemImage = findViewById(R.id.detailItemImage)
        detailItemName = findViewById(R.id.detailItemName)
        detailTopSpeed = findViewById(R.id.detailTopSpeed)
        detailEngine = findViewById(R.id.detailEngine)
        detailWeight = findViewById(R.id.detailWeight)
        detailManufacturer = findViewById(R.id.detailManufacturer)

        val item: RentalItem = intent.getParcelableExtra("selectedItem")!!
        populateDetails(item)
    }

    private fun populateDetails(item: RentalItem) {
        with(item) {
            detailItemName.text = name
            detailItemImage.setImageResource(imageResId)
            detailTopSpeed.text = "Top Speed: $topSpeed"
            detailEngine.text = "Engine: $engine"
            detailWeight.text = "Weight: $weight"
            detailManufacturer.text = "Manufacturer: $manufacturer"
        }
    }
}
