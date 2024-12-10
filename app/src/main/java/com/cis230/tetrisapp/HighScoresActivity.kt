package com.cis230.tetrisapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HighScoresActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_scores)

        // Get the root view of the high scores layout
        val rootView = findViewById<ConstraintLayout>(R.id.activity_high_scores) // Update the ID to match the root ID of your high scores layout

        // Retrieve the saved background color from SharedPreferences
        val sharedPreferences = getSharedPreferences("TetrisAppPreferences", Context.MODE_PRIVATE)
        val savedBackgroundColor = sharedPreferences.getInt("backgroundColor", R.drawable.block_dark_grey)

        // Set the background color of the root view
        rootView.setBackgroundResource(savedBackgroundColor)

        // Initialize RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.high_scores_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get high scores from database
        val dbHelper = HighScoresDbHelper(this)
        val highScores = dbHelper.getTopHighScores()

        // Set the adapter for the RecyclerView
        val adapter = HighScoresAdapter(highScores)
        recyclerView.adapter = adapter

        // Initialize Return to Main Menu button
        val returnButton = findViewById<Button>(R.id.return_button)

        // Set click listener to navigate back to MainActivity
        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the HighScoresActivity so it doesn't remain in the back stack
        }
    }

}
