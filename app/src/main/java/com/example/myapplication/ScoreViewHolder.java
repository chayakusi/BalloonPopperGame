/*
* HighScores Android App - By Chaya Kusi for CS6326 Assignment 4 ,starting on April 4, 2023.
* NET ID: CXK210030
ScoreViewHolder class to hold and display the views of each item in the RecyclerView.
 */
package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ScoreViewHolder extends RecyclerView.ViewHolder {
    public TextView rankView, nameView, scoreView, dateView;

    public ScoreViewHolder(@NonNull View itemView) {
        super(itemView);
        rankView = itemView.findViewById(R.id.rankView);
        nameView = itemView.findViewById(R.id.nameView);
        scoreView = itemView.findViewById(R.id.scoreView);
        dateView = itemView.findViewById(R.id.dateView);
    }
}
