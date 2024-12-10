package com.cis230.tetrisapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HighScoresAdapter(private val highScores: List<HighScore>) : RecyclerView.Adapter<HighScoresAdapter.HighScoreViewHolder>()
{
    class HighScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val initialsTextView: TextView = itemView.findViewById(R.id.initials_text_view)
        val scoreTextView: TextView = itemView.findViewById(R.id.score_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_high_score, parent, false)
        return HighScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int)
    {
        val highScore = highScores[position]
        holder.initialsTextView.text = highScore.initials
        holder.scoreTextView.text = highScore.score.toString()
    }

    override fun getItemCount(): Int
    {
        return highScores.size
    }
}
