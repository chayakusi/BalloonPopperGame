/*
* HighScores Android App - By Chaya Kusi for CS6326 Assignment 4 ,starting on April 4, 2023.
* NET ID: CXK210030
* It is an app that tracks high scores of a game.
* The program shows a list of top 20 scores along with the rank, name and date.
* Users can add a new score using the ADD SCORE button on the ActionBar, where the name, score, and date fields will be entered.
* */

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Read scores from the file
        List<Score> scores;
        try {
            scores = FileIO.readScores(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Set scores to the recyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ScoreAdapter(getApplicationContext(), scores));
    }

    // Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // Pass an intent and start a new activity on clicking ADD SCORE button item on the Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.AddNew)
        {
            Intent intent = new Intent(this, AddScoreActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    // Function to handle after the Save event from the new activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            int score = Integer.parseInt(data.getStringExtra("score"));
            String dateString = data.getStringExtra("date");

            // Parse the date string into a Date object
            Date date_time = FileIO.parseDate(dateString);

            Score newScore = new Score(name, score, date_time);
            // Write the score to the file
            try {
                FileIO.writeScore(this, newScore);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Update the UI to show the new score in the list
            List<Score> scores;
            try {
                scores = FileIO.readScores(getApplicationContext());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            RecyclerView recyclerView = findViewById(R.id.recyclerview);
            recyclerView.setAdapter(new ScoreAdapter(getApplicationContext(), scores));
        }
    }


}