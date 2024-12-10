package com.cis230.tetrisapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

// Main activity that displays the initial screen of the Tetris app
class MainActivity : AppCompatActivity()
{
    // Called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the root view of the main layout
        val rootView = findViewById<ConstraintLayout>(R.id.main)

        // Retrieve the saved background color from SharedPreferences
        val sharedPreferences = getSharedPreferences("TetrisAppPreferences", Context.MODE_PRIVATE)
        val savedBackgroundColor = sharedPreferences.getInt("backgroundColor", R.drawable.block_dark_grey)

        // Set the background color of the root view
        rootView.setBackgroundResource(savedBackgroundColor)

        // Find the "Play Tetris" button in the layout
        val playTetrisButton = findViewById<Button>(R.id.play_tetris_button)

        // Set a click listener on the "Play Tetris" button to start the Game activity when clicked
        playTetrisButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }

        // Find the "Show High Scores" button in the layout
        val showHighScoresButton = findViewById<Button>(R.id.score_button)

        // Set a click listener on the "Show High Scores" button to start the HighScoresActivity when clicked
        showHighScoresButton.setOnClickListener {
            startActivity(Intent(this, HighScoresActivity::class.java))
        }

        // Find the "Settings" button in the layout
        val settingsButton = findViewById<Button>(R.id.settings_button)

        // Set a click listener on the "Settings" button to start the SettingsActivity when clicked
        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}