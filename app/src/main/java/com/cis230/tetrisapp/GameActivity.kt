package com.cis230.tetrisapp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random
import kotlin.random.nextInt


class GameActivity : AppCompatActivity()
{
    private lateinit var right: Button
    private lateinit var left: Button
    private lateinit var down: Button
    private lateinit var helpButton: ImageButton
    private lateinit var rotateRight: Button
    private lateinit var emptyTextView: TextView
    private lateinit var pointsTally: TextView

    private lateinit var mainB: List<TextView>
    private lateinit var sideB: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Get the root view of the game layout
        val rootView = findViewById<ConstraintLayout>(R.id.activity_game) // Update the ID to match the root ID of your game layout

        // Retrieve the saved background color from SharedPreferences
        val sharedPreferences = getSharedPreferences("TetrisAppPreferences", Context.MODE_PRIVATE)
        val savedBackgroundColor = sharedPreferences.getInt("backgroundColor", R.drawable.block_dark_grey)

        // Set the background color of the root view
        rootView.setBackgroundResource(savedBackgroundColor)

        // Initialize help button
        helpButton = findViewById(R.id.help_button)
        helpButton.setOnClickListener {
            gameOver = true
            showHelpDialog() // Show the help dialog when help button is clicked
        }

        // Initialize control buttons
        right = findViewById(R.id.button_right)
        left = findViewById(R.id.button_left)
        down = findViewById(R.id.button_down)
        rotateRight = findViewById(R.id.button_rotate_right)
        emptyTextView = findViewById(R.id.emptyTextView)
        pointsTally = findViewById(R.id.point_tally)

        // Initialize side buttons
        sideB = listOf(emptyTextView,
            findViewById(R.id.side_b1), findViewById(R.id.side_b2), findViewById(R.id.side_b3),
            findViewById(R.id.side_b4), findViewById(R.id.side_b5), findViewById(R.id.side_b6),
            findViewById(R.id.side_b7), findViewById(R.id.side_b8), findViewById(R.id.side_b9),
            findViewById(R.id.side_b10), findViewById(R.id.side_b11), findViewById(R.id.side_b12)
        )

        // Initialize main buttons
        mainB = listOf(emptyTextView,
            findViewById(R.id.b1), findViewById(R.id.b2), findViewById(R.id.b3), findViewById(R.id.b4), findViewById(R.id.b5),
            findViewById(R.id.b6), findViewById(R.id.b7), findViewById(R.id.b8), findViewById(R.id.b9), findViewById(R.id.b10),
            findViewById(R.id.b11), findViewById(R.id.b12), findViewById(R.id.b13), findViewById(R.id.b14), findViewById(R.id.b15),
            findViewById(R.id.b16), findViewById(R.id.b17), findViewById(R.id.b18), findViewById(R.id.b19), findViewById(R.id.b20),
            findViewById(R.id.b21), findViewById(R.id.b22), findViewById(R.id.b23), findViewById(R.id.b24), findViewById(R.id.b25),
            findViewById(R.id.b26), findViewById(R.id.b27), findViewById(R.id.b28), findViewById(R.id.b29), findViewById(R.id.b30),
            findViewById(R.id.b31), findViewById(R.id.b32), findViewById(R.id.b33), findViewById(R.id.b34), findViewById(R.id.b35),
            findViewById(R.id.b36), findViewById(R.id.b37), findViewById(R.id.b38), findViewById(R.id.b39), findViewById(R.id.b40),
            findViewById(R.id.b41), findViewById(R.id.b42), findViewById(R.id.b43), findViewById(R.id.b44), findViewById(R.id.b45),
            findViewById(R.id.b46), findViewById(R.id.b47), findViewById(R.id.b48), findViewById(R.id.b49), findViewById(R.id.b50),
            findViewById(R.id.b51), findViewById(R.id.b52), findViewById(R.id.b53), findViewById(R.id.b54), findViewById(R.id.b55),
            findViewById(R.id.b56), findViewById(R.id.b57), findViewById(R.id.b58), findViewById(R.id.b59), findViewById(R.id.b60),
            findViewById(R.id.b61), findViewById(R.id.b62), findViewById(R.id.b63), findViewById(R.id.b64), findViewById(R.id.b65),
            findViewById(R.id.b66), findViewById(R.id.b67), findViewById(R.id.b68), findViewById(R.id.b69), findViewById(R.id.b70),
            findViewById(R.id.b71), findViewById(R.id.b72), findViewById(R.id.b73), findViewById(R.id.b74), findViewById(R.id.b75),
            findViewById(R.id.b76), findViewById(R.id.b77), findViewById(R.id.b78), findViewById(R.id.b79), findViewById(R.id.b80),
            findViewById(R.id.b81), findViewById(R.id.b82), findViewById(R.id.b83), findViewById(R.id.b84), findViewById(R.id.b85),
            findViewById(R.id.b86), findViewById(R.id.b87), findViewById(R.id.b88), findViewById(R.id.b89), findViewById(R.id.b90),
            findViewById(R.id.b91), findViewById(R.id.b92), findViewById(R.id.b93), findViewById(R.id.b94), findViewById(R.id.b95),
            findViewById(R.id.b96), findViewById(R.id.b97), findViewById(R.id.b98), findViewById(R.id.b99), findViewById(R.id.b100),
            findViewById(R.id.b101), findViewById(R.id.b102), findViewById(R.id.b103), findViewById(R.id.b104), findViewById(R.id.b105),
            findViewById(R.id.b106), findViewById(R.id.b107), findViewById(R.id.b108), findViewById(R.id.b109), findViewById(R.id.b110),
            findViewById(R.id.b111), findViewById(R.id.b112), findViewById(R.id.b113), findViewById(R.id.b114), findViewById(R.id.b115),
            findViewById(R.id.b116), findViewById(R.id.b117), findViewById(R.id.b118), findViewById(R.id.b119), findViewById(R.id.b120),
            findViewById(R.id.b121), findViewById(R.id.b122), findViewById(R.id.b123), findViewById(R.id.b124), findViewById(R.id.b125),
            findViewById(R.id.b126), findViewById(R.id.b127), findViewById(R.id.b128), findViewById(R.id.b129), findViewById(R.id.b130),
            findViewById(R.id.b131), findViewById(R.id.b132), findViewById(R.id.b133), findViewById(R.id.b134), findViewById(R.id.b135),
            findViewById(R.id.b136), findViewById(R.id.b137), findViewById(R.id.b138), findViewById(R.id.b139), findViewById(R.id.b140),
            findViewById(R.id.b141), findViewById(R.id.b142), findViewById(R.id.b143), findViewById(R.id.b144), findViewById(R.id.b145),
            findViewById(R.id.b146), findViewById(R.id.b147), findViewById(R.id.b148), findViewById(R.id.b149), findViewById(R.id.b150)
        )

        // Set the initial shape
        nextShape()
    }

    private var nextUp: Int = 0
    private var start = 0
    private var points = 0
    private var pos1 = 0
    private var pos2 = 0
    private var pos3 = 0
    private var pos4 = 0
    private var shapeIs = 0
    private var stop = 0
    private var line = 0
    private var isHelpDialogOpen = false

    private fun deleteLine()
    {
        val clearedLines = mutableListOf<Int>()

        // Identify all cleared lines
        for (start in 11..141 step 10)
        {
            val currentRange = start until start + 10
            if (currentRange.all { mainB[it].text == "0" })
            {
                points += 100
                clearedLines.add(start) // Add the cleared line start index to the list

                // Clear the current line
                for (i in currentRange)
                {
                    mainB[i].setBackgroundResource(R.drawable.grid_block)
                    mainB[i].text = ""
                }
            }
        }

        // If we have cleared lines, drop all blocks above them
        if (clearedLines.isNotEmpty())
        {
            Handler(Looper.getMainLooper()).postDelayed({
                // Start from the top of the grid, iterate downward
                for (currentLineStart in 141 downTo 11 step 10)
                {
                    val linesClearedBelow = clearedLines.count { it > currentLineStart }
                    if (linesClearedBelow > 0)
                    {
                        val currentRange = currentLineStart until currentLineStart + 10
                        val targetRange = (currentLineStart + linesClearedBelow * 10) until (currentLineStart + linesClearedBelow * 10 + 10)

                        // Move the blocks down by the number of cleared lines if they are not part of the falling piece
                        if (targetRange.first <= 150)
                        {
                            for (i in currentRange)
                            {
                                if (i !in listOf(pos1, pos2, pos3, pos4))
                                { // Exclude currently falling shape
                                    val targetIndex = i + (linesClearedBelow * 10)
                                    if (targetIndex <= 150)
                                    {
                                        mainB[targetIndex].text = mainB[i].text
                                        mainB[targetIndex].background = mainB[i].background

                                        // Clear the current position
                                        mainB[i].setBackgroundResource(R.drawable.grid_block)
                                        mainB[i].text = ""
                                    }
                                }
                            }
                        }
                    }
                }

                // Update points display
                pointsTally.text = "$points"
            }, 500) // Adding a delay to make the dropping visually less jarring
        }

        else
        {
            // Update points display immediately if no lines are cleared
            pointsTally.text = "$points"
        }
    }

    private fun nextShape()
    {
        // Reset all side buttons to the default background resource
        sideB.forEach { button ->
            button.setBackgroundResource(R.drawable.grid_block)
        }

        // Generate a random number between 1 and 7 to determine the next shape
        val random = Random.nextInt(1..7)
        // Optional code to restrict shape selection to specific values
        //val options = listOf(3, 7)
        //val random = options.random()

        // Set the shape based on the generated random number
        when (random)
        {
            1 ->
            {
                // Set red shape
                sideB[6].setBackgroundResource(R.drawable.red)
                sideB[7].setBackgroundResource(R.drawable.red)
                sideB[8].setBackgroundResource(R.drawable.red)
                sideB[9].setBackgroundResource(R.drawable.red)
            }

            2 ->
            {
                // Set orange shape
                sideB[4].setBackgroundResource(R.drawable.orange)
                sideB[5].setBackgroundResource(R.drawable.orange)
                sideB[8].setBackgroundResource(R.drawable.orange)
                sideB[9].setBackgroundResource(R.drawable.orange)
            }

            3 ->
            {
                // Set yellow shape
                sideB[5].setBackgroundResource(R.drawable.yellow)
                sideB[6].setBackgroundResource(R.drawable.yellow)
                sideB[8].setBackgroundResource(R.drawable.yellow)
                sideB[9].setBackgroundResource(R.drawable.yellow)
            }

            4 ->
            {
                // Set green shape
                sideB[4].setBackgroundResource(R.drawable.green)
                sideB[7].setBackgroundResource(R.drawable.green)
                sideB[8].setBackgroundResource(R.drawable.green)
                sideB[9].setBackgroundResource(R.drawable.green)
            }

            5 ->
            {
                // Set blue shape
                sideB[5].setBackgroundResource(R.drawable.blue)
                sideB[6].setBackgroundResource(R.drawable.blue)
                sideB[7].setBackgroundResource(R.drawable.blue)
                sideB[8].setBackgroundResource(R.drawable.blue)
            }

            6 ->
            {
                // Set purple shape
                sideB[5].setBackgroundResource(R.drawable.purple)
                sideB[7].setBackgroundResource(R.drawable.purple)
                sideB[8].setBackgroundResource(R.drawable.purple)
                sideB[9].setBackgroundResource(R.drawable.purple)
            }

            7 ->
            {
                // Set pink shape
                sideB[2].setBackgroundResource(R.drawable.pink)
                sideB[5].setBackgroundResource(R.drawable.pink)
                sideB[8].setBackgroundResource(R.drawable.pink)
                sideB[11].setBackgroundResource(R.drawable.pink)
            }
        }

        // Store the generated shape for future reference
        nextUp = random

        // If this is the initial state, start generating shapes with a delay of 1.5 seconds
        if (start == 0)
        {
            Handler(Looper.getMainLooper()).postDelayed({ shapes() }, 1500)
            start = 1
        }
    }

    private var gameOver = false

    private fun shapes()
    {
        // If the game is over, exit immediately
        if (gameOver) return

        // Clear lines if needed, only when no shape is falling
        if (stop == 0)
        {
            deleteLine()
        }

        // If the game is not over and ready for the next shape
        if (stop == 0)
        {
            // Reset control variables
            a = 0
            b = 0
            x = 0
            z = 0

            // Clear any stored shape values to prepare for the next shape
            valueArray.clear()
            arrayCollectPreviousOne.clear()

            // Set the next shape to be displayed
            shapeIs = nextUp
            nextShape()

            // Set the new shape at the initial position
            when (shapeIs)
            {
                1 -> {
                    // Place the red shape at the initial positions
                    mainB[6].setBackgroundResource(R.drawable.red)
                    mainB[14].setBackgroundResource(R.drawable.red)
                    mainB[15].setBackgroundResource(R.drawable.red)
                    mainB[16].setBackgroundResource(R.drawable.red)
                    pos1 = 6; pos2 = 14; pos3 = 15; pos4 = 16
                }
                2 -> {
                    // Place the orange shape at the initial positions
                    mainB[5].setBackgroundResource(R.drawable.orange)
                    mainB[6].setBackgroundResource(R.drawable.orange)
                    mainB[16].setBackgroundResource(R.drawable.orange)
                    mainB[17].setBackgroundResource(R.drawable.orange)
                    pos1 = 5; pos2 = 6; pos3 = 16; pos4 = 17
                }
                3 -> {
                    // Place the yellow shape at the initial positions
                    mainB[5].setBackgroundResource(R.drawable.yellow)
                    mainB[6].setBackgroundResource(R.drawable.yellow)
                    mainB[15].setBackgroundResource(R.drawable.yellow)
                    mainB[16].setBackgroundResource(R.drawable.yellow)
                    pos1 = 5; pos2 = 6; pos3 = 15; pos4 = 16
                }
                4 -> {
                    // Place the green shape at the initial positions
                    mainB[5].setBackgroundResource(R.drawable.green)
                    mainB[15].setBackgroundResource(R.drawable.green)
                    mainB[16].setBackgroundResource(R.drawable.green)
                    mainB[17].setBackgroundResource(R.drawable.green)
                    pos1 = 5; pos2 = 15; pos3 = 16; pos4 = 17
                }
                5 -> {
                    // Place the blue shape at the initial positions
                    mainB[5].setBackgroundResource(R.drawable.blue)
                    mainB[6].setBackgroundResource(R.drawable.blue)
                    mainB[14].setBackgroundResource(R.drawable.blue)
                    mainB[15].setBackgroundResource(R.drawable.blue)
                    pos1 = 5; pos2 = 6; pos3 = 14; pos4 = 15
                }
                6 -> {
                    // Place the purple shape at the initial positions
                    mainB[6].setBackgroundResource(R.drawable.purple)
                    mainB[15].setBackgroundResource(R.drawable.purple)
                    mainB[16].setBackgroundResource(R.drawable.purple)
                    mainB[17].setBackgroundResource(R.drawable.purple)
                    pos1 = 6; pos2 = 15; pos3 = 16; pos4 = 17
                }
                7 -> {
                    // Place the pink shape at the initial positions
                    mainB[4].setBackgroundResource(R.drawable.pink)
                    mainB[5].setBackgroundResource(R.drawable.pink)
                    mainB[6].setBackgroundResource(R.drawable.pink)
                    mainB[7].setBackgroundResource(R.drawable.pink)
                    pos1 = 4; pos2 = 5; pos3 = 6; pos4 = 7
                }
            }

            // Check for game over condition after placing the shape
            if (isGameOverCondition())
            {
                // Delay to allow visibility of the final block before showing game over dialog
                Handler(Looper.getMainLooper()).postDelayed({
                    showGameOverDialog()
                }, 500)
            }
            else
            {
                // Delay before starting the movement of the shape to make it smoother
                Handler(Looper.getMainLooper()).postDelayed({
                    if (!gameOver)
                    {
                        controller()
                    }
                }, 500) // 500ms delay for smoother visibility of the new shape
            }
        }
    }

    private fun isGameOverCondition(): Boolean
    {
        // Game over condition: Check if any block in row 15 or 14 is occupied
        return (11..20).any { mainB[it].text == "0" }
    }

    private fun showHelpDialog()
    {
        isHelpDialogOpen = true // Set the flag to true when the help dialog opens

        // Inflate the help dialog layout
        val dialogView = layoutInflater.inflate(R.layout.help_dialog, null)

        // Create the AlertDialog with the custom view
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false) // Ensure the dialog cannot be dismissed by back button
            .create()

        // Initialize the close button in the dialog and set a click listener
        val restartButton = dialogView.findViewById<Button>(R.id.restart_button)
        restartButton.setOnClickListener {
            dialog.dismiss() // Dismiss the dialog when the button is clicked
            finish()
            isHelpDialogOpen = false // Set the flag to false when the help dialog closes
            startActivity(intent) // Restart GameActivity
        }

        // Show the dialog
        dialog.show()
    }


    private fun showGameOverDialog()
    {
        // If the game is over or if help dialog is open, do not show the game over dialog
        if (gameOver || isHelpDialogOpen) return

        gameOver = true

        // Inflate the custom dialog layout
        val dialogView = layoutInflater.inflate(R.layout.game_over_dialog, null)

        // Create the dialog and set the custom view
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Find the TextView in the inflated dialog view and set the points text
        val gameOverPointsTally = dialogView.findViewById<TextView>(R.id.game_over_points_display)
        gameOverPointsTally.text = getString(R.string.points_display, points)

        // Main Menu button
        dialogView.findViewById<Button>(R.id.main_menu_button).setOnClickListener {
            // Return to main menu
            finish()
        }

        // Play Again button
        dialogView.findViewById<Button>(R.id.play_again_button).setOnClickListener {
            // Restart game
            restartGame()
            dialog.dismiss()
        }

        // Save Score button to display initials input dialog
        dialogView.findViewById<Button>(R.id.save_score_button).setOnClickListener {
            // Inflate the initials input dialog layout
            val initialsView = layoutInflater.inflate(R.layout.enter_initials_dialog_box, null)

            // Create the initials input dialog
            val initialsDialog = AlertDialog.Builder(this)
                .setView(initialsView)
                .create()

            // Handle saving initials
            initialsView.findViewById<Button>(R.id.save_initials_button).setOnClickListener {
                val initialsInput = initialsView.findViewById<EditText>(R.id.initials_input)
                val initials = initialsInput.text.toString().trim()

                if (initials.length == 3)
                {
                    // Save the high score to the database
                    val dbHelper = HighScoresDbHelper(this)
                    dbHelper.insertHighScore(initials, points)
                    Toast.makeText(this, "Score saved!", Toast.LENGTH_SHORT).show()
                    initialsDialog.dismiss()
                }
                else
                {
                    Toast.makeText(this, "Please enter exactly 3 initials.", Toast.LENGTH_SHORT).show()
                }
            }

            // Handle cancel button click
            initialsView.findViewById<Button>(R.id.cancel_button).setOnClickListener {
                // Dismiss the initials input dialog
                initialsDialog.dismiss()
            }

            initialsDialog.show()
        }

        // Show the dialog
        dialog.show()
    }


    private fun restartGame()
    {
        // Clear all blocks on the main board
        mainB.forEach {
            it.setBackgroundResource(R.drawable.grid_block)
            it.text = ""
        }

        // Reset all game variables to their initial state
        stop = 0
        a = 0
        b = 0
        x = 0
        z = 0
        points = 0
        gameOver = false
        start = 0
        line = 0

        // Clear arrays used for tracking shape positions
        valueArray.clear()
        arrayCollectPreviousOne.clear()

        // Reset points display on the UI
        pointsTally.text = "$points"

        // Start the next shape to continue the game
        nextShape()
    }

    private val arrayCollectPreviousOne = arrayListOf<TextView>()
    private val valueArray = arrayListOf<TextView>()
    private var a = 0
    private var b = 0
    private var x = 0
    private var z = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean
    {
        // Handle directional key presses for game controls
        when (keyCode)
        {
            KeyEvent.KEYCODE_DPAD_RIGHT -> moveRight()   // Move the shape to the right
            KeyEvent.KEYCODE_DPAD_LEFT -> moveLeft()     // Move the shape to the left
            KeyEvent.KEYCODE_DPAD_DOWN -> moveDown()     // Drop the shape faster
            KeyEvent.KEYCODE_DPAD_UP -> rotate()         // Rotate the shape
            else -> return super.onKeyDown(keyCode, event) // For any other key, use default behavior
        }

        // Return true to indicate the key event was handled
        return true
    }


    private fun moveRight()
    {
        // Only allow movement if 'a' is 0 (indicating no other constraints are preventing movement)
        if (a == 0)
        {
            // Define the boundary indices for the right edge of the grid
            val boundaryIndices = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150)

            // Check if any part of the shape is at the boundary or if the adjacent blocks are occupied
            if (pos1 !in boundaryIndices && pos2 !in boundaryIndices && pos3 !in boundaryIndices && pos4 !in boundaryIndices
                && mainB[pos1 + 1].text == "" && mainB[pos2 + 1].text == ""
                && mainB[pos3 + 1].text == "" && mainB[pos4 + 1].text == "")
            {
                // Move the shape one position to the right
                pos1 += 1
                pos2 += 1
                pos3 += 1
                pos4 += 1
            }
        }
    }

    private fun moveLeft()
    {
        // Only allow movement if 'b' is 0 (indicating no other constraints are preventing movement)
        if (b == 0)
        {
            // Define the boundary indices for the left edge of the grid
            val boundaryIndices = listOf(1, 11, 21, 31, 41, 51, 61, 71, 81, 91, 101, 111, 121, 131, 141)

            // Check if any part of the shape is at the boundary or if the adjacent blocks to the left are occupied
            if (pos1 !in boundaryIndices && pos2 !in boundaryIndices && pos3 !in boundaryIndices && pos4 !in boundaryIndices
                && mainB[pos1 - 1].text == "" && mainB[pos2 - 1].text == ""
                && mainB[pos3 - 1].text == "" && mainB[pos4 - 1].text == "")
            {
                // Move the shape one position to the left
                pos1 -= 1
                pos2 -= 1
                pos3 -= 1
                pos4 -= 1
            }
        }
    }

    private fun moveDown()
    {
        // Move the shape down until it reaches an obstacle or the bottom of the grid
        while (x == 0)
        {
            // Add the current position of the shape to the previous positions array
            arrayCollectPreviousOne.add(mainB[pos1])
            arrayCollectPreviousOne.add(mainB[pos2])
            arrayCollectPreviousOne.add(mainB[pos3])
            arrayCollectPreviousOne.add(mainB[pos4])

            // Move each part of the shape one row down
            pos1 += 10
            pos2 += 10
            pos3 += 10
            pos4 += 10

            // Create a list of the new positions of the shape
            val positions = listOf(pos1, pos2, pos3, pos4)

            // Check if the new positions overlap with filled blocks or reach the bottom boundary
            if (positions.any { mainB[it].text == "1" } || positions.any { it >= 141 })
            {
                // Move the shape back up one row
                pos1 -= 10
                pos2 -= 10
                pos3 -= 10
                pos4 -= 10

                // Set 'x' to 1 to indicate that the shape has landed
                x = 1

                // Award points for moving down successfully
                points += 5
            }
        }
    }

    private fun rotate()
    {
        val columns = 10
        val maxIndex = mainB.size - 1

        // Helper function to check if the proposed new positions are valid for rotation
        fun isValidRotation(vararg positions: Int): Boolean
        {
            return positions.all { it in 0..maxIndex && mainB[it].text == "" }
        }

        // Determine the new positions based on the current shape and its rotation state
        val newPositions = when (shapeIs)
        {
            // Rotation positions for shape 1
            1 -> when (z)
            {
                0 -> intArrayOf(pos1 - 1, pos2 + 1, pos3 + columns, pos4 + columns)
                1 -> intArrayOf(pos1 + 9, pos2, pos3 - 9, pos4 - 2)
                2 -> intArrayOf(pos1 - columns, pos2 - columns, pos3 - 1, pos4 + 1)
                else -> intArrayOf(pos1 + 2, pos2 + 9, pos3, pos4 - 9)
            }

            // Rotation positions for shape 2
            2 -> when (z)
            {
                0 -> intArrayOf(pos1 + 1, pos2 + 9, pos3, pos4 + 8)
                else -> intArrayOf(pos1 - 1, pos2 - 9, pos3, pos4 - 8)
            }

            // Shape 3 has no rotation
            3 -> intArrayOf() // No rotation for shape 3

            // Rotation positions for shape 4
            4 -> when (z)
            {
                0 -> intArrayOf(pos1, pos2 - 9, pos3 - 1, pos4 + 8)
                1 -> intArrayOf(pos1 + columns, pos2 + columns, pos3 + 2, pos4 + 2)
                2 -> intArrayOf(pos1 - 8, pos2 + 1, pos3 + 9, pos4)
                else -> intArrayOf(pos1 - 2, pos2 - 2, pos3 - columns, pos4 - columns)
            }

            // Rotation positions for shape 5
            5 -> when (z)
            {
                0 -> intArrayOf(pos1, pos2 + 9, pos3 + 2, pos4 + 11)
                else -> intArrayOf(pos1, pos2 - 9, pos3 - 2, pos4 - 11)
            }

            // Rotation positions for shape 6
            6 -> when (z)
            {
                0 -> intArrayOf(pos1, pos2 + 1, pos3 + 1, pos4 + 9)
                1 -> intArrayOf(pos1 + 9)
                2 -> intArrayOf(pos1 - 9, pos2 - 1, pos3 - 1)
                else -> intArrayOf(pos4 - 9)
            }

            // Rotation positions for shape 7
            7 -> when (z)
            {
                0 -> intArrayOf(pos1 + 1, pos2 + 10, pos3 + 19, pos4 + 28)
                else -> intArrayOf(pos1 - 1, pos2 - 10, pos3 - 19, pos4 - 28)
            }

            else -> intArrayOf() // Default case for unexpected shape values
        }

        // Apply the new positions if they are valid
        if (isValidRotation(*newPositions))
        {
            // Update the positions for each shape based on the current rotation state
            when (shapeIs)
            {
                1 -> when (z)
                {
                    0 -> {
                        // Update the positions for rotation 0 to 1
                        pos1 -= 1; pos2 += 1; pos3 += columns; pos4 += columns; z = 1
                    }
                    1 -> {
                        // Update the positions for rotation 1 to 2
                        pos1 += 9; pos3 -= 9; pos4 -= 2; z = 2
                    }
                    2 -> {
                        // Update the positions for rotation 2 to 3
                        pos1 -= columns; pos2 -= columns; pos3 -= 1; pos4 += 1; z = 3
                    }
                    3 -> {
                        // Update the positions for rotation 3 to 0
                        pos1 += 2; pos2 += 9; pos4 -= 9; z = 0
                    }
                }

                2 -> when (z)
                {
                    0 -> {
                        // Update the positions for rotation 0 to 1
                        pos1 += 1; pos2 += 9; pos4 += 8; z = 1
                    }
                    1 -> {
                        // Update the positions for rotation 1 to 0
                        pos1 -= 1; pos2 -= 9; pos4 -= 8; z = 0
                    }
                }

                4 -> when (z)
                {
                    0 -> {
                        // Update the positions for rotation 0 to 1
                        pos2 -= 9; pos3 -= 1; pos4 += 8; z = 1
                    }
                    1 -> {
                        // Update the positions for rotation 1 to 2
                        pos1 += columns; pos2 += columns; pos3 += 2; pos4 += 2; z = 2
                    }
                    2 -> {
                        // Update the positions for rotation 2 to 3
                        pos1 -= 8; pos2 += 1; pos3 += 9; z = 3
                    }
                    3 -> {
                        // Update the positions for rotation 3 to 0
                        pos1 -= 2; pos2 -= 2; pos3 -= columns; pos4 -= columns; z = 0
                    }
                }

                5 -> when (z)
                {
                    0 -> {
                        // Update the positions for rotation 0 to 1
                        pos2 += 9; pos3 += 2; pos4 += 11; z = 1
                    }
                    1 -> {
                        // Update the positions for rotation 1 to 0
                        pos2 -= 9; pos3 -= 2; pos4 -= 11; z = 0
                    }
                }

                6 -> when (z)
                {
                    0 -> {
                        // Update the positions for rotation 0 to 1
                        pos2 += 1; pos3 += 1; pos4 += 9; z = 1
                    }
                    1 -> {
                        // Update the positions for rotation 1 to 2
                        pos1 += 9; z = 2
                    }
                    2 -> {
                        // Update the positions for rotation 2 to 3
                        pos1 -= 9; pos2 -= 1; pos3 -= 1; z = 3
                    }
                    3 -> {
                        // Update the positions for rotation 3 to 0
                        pos4 -= 9; z = 0
                    }
                }

                7 -> when (z)
                {
                    0 -> {
                        // Update the positions for rotation 0 to 1
                        pos1 += 1; pos2 += 10; pos3 += 19; pos4 += 28; z = 1
                    }
                    1 -> {
                        // Update the positions for rotation 1 to 0
                        pos1 -= 1; pos2 -= 10; pos3 -= 19; pos4 -= 28; z = 0
                    }
                }
            }
        }
    }

    private fun controller()
    {
        // Set up click listeners for the control buttons
        right.setOnClickListener { moveRight() }    // Move shape to the right when the right button is clicked
        left.setOnClickListener { moveLeft() }      // Move shape to the left when the left button is clicked
        down.setOnClickListener { moveDown() }      // Move shape down when the down button is clicked
        rotateRight.setOnClickListener { rotate() } // Rotate the shape when the rotate button is clicked

        // Move the shape down initially if conditions 'a' or 'b' are zero
        if (a == 0 || b == 0)
        {
            pos1 += 10
            pos2 += 10
            pos3 += 10
            pos4 += 10
        }

        // Add the current positions of the shape to the collection of previous positions
        arrayCollectPreviousOne.add(mainB[pos1])
        arrayCollectPreviousOne.add(mainB[pos2])
        arrayCollectPreviousOne.add(mainB[pos3])
        arrayCollectPreviousOne.add(mainB[pos4])

        // Store the current positions in the value array for further processing
        valueArray.add(mainB[pos1])
        valueArray.add(mainB[pos2])
        valueArray.add(mainB[pos3])
        valueArray.add(mainB[pos4])

        // Check if the shape has landed and apply appropriate logic
        landing()

        // Set the color of the blocks based on the shape
        colors()
    }

    private fun blockState()
    {
        // Create a list of the current positions of the shape
        val positions = listOf(pos1, pos2, pos3, pos4)

        // For each position in the list, update the state of the block above it
        positions.forEach { pos ->
            val targetBlock = mainB[pos - 10] // Get the block directly above the current position

            // If the block is empty, set its text to "1" to mark it as occupied
            if (targetBlock.text == "")
            {
                targetBlock.text = "1"
            }
        }

        // Call the shapes function to proceed with the next shape or game logic
        shapes()
    }

    private fun resetNonZeroBlocks()
    {
        // Iterate over all blocks in the grid, skipping the first one
        mainB.drop(1).forEach { button ->
            // If the block's text is not "0", reset its background to the default grid block
            if (button.text != "0")
            {
                button.setBackgroundResource(R.drawable.grid_block)
            }
        }
    }

    private fun landing()
    {
        // Create a list of the current positions of the shape
        val positions = listOf(pos1, pos2, pos3, pos4)

        // Check if any part of the shape has reached the bottom boundary (rows 141 to 150)
        if (positions.any { it in 141..150 } || valueArray.any { it.text == "1" })
        {
            // Mark all blocks in valueArray as landed by setting their text to "0"
            valueArray.forEach { it.text = "0" }
        }
    }

    private fun colors()
    {
        // Reset the background of all blocks that are not marked as "0"
        resetNonZeroBlocks()

        // Reset movement flags
        a = 0
        b = 0

        // Set the background of all blocks in the previous shape position to the default grid block
        arrayCollectPreviousOne.forEach { it.setBackgroundResource(R.drawable.grid_block) }

        // Determine the color resource for the current shape
        val colorResource = when (shapeIs)
        {
            1 -> R.drawable.red
            2 -> R.drawable.orange
            3 -> R.drawable.yellow
            4 -> R.drawable.green
            5 -> R.drawable.blue
            6 -> R.drawable.purple
            7 -> R.drawable.pink
            else -> R.drawable.grid_block
        }

        // Set the background of the current shape's blocks to the appropriate color
        valueArray.forEach { it.setBackgroundResource(colorResource) }

        // Check if all blocks in valueArray are marked as "0"
        if (valueArray.all { it.text == "0" })
        {
            // If so, increase the points and call blockState()
            points += 15
            blockState()
        }
        else
        {
            // Otherwise, clear valueArray and arrayCollectPreviousOne, and restart the controller after a delay
            valueArray.clear()
            arrayCollectPreviousOne.clear()
            Handler(Looper.getMainLooper()).postDelayed({ controller() }, 600)
        }
    }
}