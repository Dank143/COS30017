package com.example.assignment3

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MeetingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val meetings = parseCSV()
        adapter = MeetingAdapter(meetings)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter_tech -> {
                adapter.filter("Online")
                true
            }
            R.id.action_filter_tech2 -> {
                adapter.filter("In-person")
                true
            }
            R.id.action_filter_all -> {
                adapter.filter("All")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun parseCSV(): List<Meeting> {
        val inputStream = resources.openRawResource(R.raw.groups)
        val lines = inputStream.bufferedReader().readLines()

        return lines.drop(1).map {
            val parts = it.split(",")
            Meeting(parts[1], parts[2], parts[3], parts[4])
        }
    }
}
