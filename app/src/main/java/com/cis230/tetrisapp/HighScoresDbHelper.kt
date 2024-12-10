package com.cis230.tetrisapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Database helper class to manage the high scores database
class HighScoresDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object
    {
        // Database constants
        const val DATABASE_NAME = "HighScores.db"
        const val DATABASE_VERSION = 1

        // Table and column names
        const val TABLE_NAME = "high_scores"
        const val COLUMN_ID = "id"
        const val COLUMN_INITIALS = "initials"
        const val COLUMN_SCORE = "score"
    }

    // SQL query to create the high_scores table
    private val sqlCreateEntries = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_INITIALS TEXT NOT NULL,
            $COLUMN_SCORE INTEGER NOT NULL
        )
    """.trimIndent()

    // SQL query to delete the high_scores table
    private val sqlDeleteEntries = "DROP TABLE IF EXISTS $TABLE_NAME"

    // Called when the database is created for the first time
    override fun onCreate(db: SQLiteDatabase)
    {
        db.execSQL(sqlCreateEntries) // Create the high_scores table
    }

    // Called when the database needs to be upgraded (e.g., schema changes)
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        db.execSQL(sqlDeleteEntries) // Delete the existing table
        onCreate(db) // Recreate the table with the new schema
    }

    // Method to insert a high score into the database
    fun insertHighScore(initials: String, score: Int)
    {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_INITIALS, initials)
            put(COLUMN_SCORE, score)
        }
        db.insert(TABLE_NAME, null, values)
    }

    // Method to get the top 10 high scores
    fun getTopHighScores(): List<HighScore>
    {
        val db = readableDatabase
        val highScores = mutableListOf<HighScore>()

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_SCORE DESC LIMIT 10",
            null
        )

        with(cursor)
        {
            while (moveToNext())
            {
                val initials = getString(getColumnIndexOrThrow(COLUMN_INITIALS))
                val score = getInt(getColumnIndexOrThrow(COLUMN_SCORE))
                highScores.add(HighScore(initials, score))
            }
            close()
        }

        return highScores
    }


    // Method to delete all high scores
    fun deleteAllHighScores()
    {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

    // Method to delete a specific high score by ID
    fun deleteHighScoreById(id: Long)
    {
        val db = writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(id.toString())
        db.delete(TABLE_NAME, selection, selectionArgs)
    }

    // Close the database
    fun closeDb()
    {
        this.close()
    }
}
