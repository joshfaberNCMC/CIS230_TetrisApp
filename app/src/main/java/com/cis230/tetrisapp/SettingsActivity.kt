package com.cis230.tetrisapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class SettingsActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Get SharedPreferences to save user preferences
        val sharedPreferences = getSharedPreferences("TetrisAppPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Load the saved background color drawable resource ID
        val savedDrawableResource = sharedPreferences.getInt("backgroundColor", R.drawable.block_dark_grey)

        // Find root view
        val rootView = findViewById<ConstraintLayout>(R.id.activity_settings)

        // Set the root view's background to the saved background drawable resource
        rootView.setBackgroundResource(savedDrawableResource)

        // Map of color buttons and their corresponding drawable backgrounds
        val colorButtonMap = mapOf(
            R.id.button_color_black to R.drawable.block_black,
            R.id.button_color_blue to R.drawable.block_blue,
            R.id.button_color_dark_grey to R.drawable.block_dark_grey,
            R.id.button_color_green to R.drawable.block_green,
            R.id.button_color_grey to R.drawable.block_grey,
            R.id.button_color_orange to R.drawable.block_orange,
            R.id.button_color_pink to R.drawable.block_pink,
            R.id.button_color_purple to R.drawable.block_purple,
            R.id.button_color_red to R.drawable.block_red,
            R.id.button_color_turquoise to R.drawable.block_turquoise,
            R.id.button_color_white to R.drawable.block_white,
            R.id.button_color_yellow to R.drawable.block_yellow
        )

        // Set click listeners for each color button to save the selected background color
        colorButtonMap.forEach { (buttonId, colorDrawable) ->
            findViewById<Button>(buttonId).setOnClickListener {
                // Save the selected background drawable resource ID to SharedPreferences
                editor.putInt("backgroundColor", colorDrawable)
                editor.apply()

                // Update the background color of the current activity immediately
                rootView.setBackgroundResource(colorDrawable)
            }
        }

        // Return to Main Menu button functionality
        val returnButton = findViewById<Button>(R.id.return_button)
        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}