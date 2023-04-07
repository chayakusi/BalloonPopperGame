package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Score> scores;
        try {
            scores = FileIO.readScores(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ScoreAdapter(getApplicationContext(), scores));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

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
//            adapter.setScores(scores);
            List<Score> scores = null;
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