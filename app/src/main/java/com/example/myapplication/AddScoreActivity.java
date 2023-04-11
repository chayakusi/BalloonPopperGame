package com.example.myapplication;
/*
* HighScores Android App - By Chaya Kusi for CS6326 Assignment 4 ,starting on April 4, 2023.
* NET ID: CXK210030
* AddScoreActivity Class for Adding Score
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddScoreActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextScore;
    private EditText editTextDate;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);

        // Set date/time to current date and time
        EditText dateEditText = findViewById(R.id.editTextDate);
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        String dateString = dateFormat.format(currentDate);
        dateEditText.setText(dateString);

        // Find views
        editTextName = findViewById(R.id.editTextName);
        editTextScore = findViewById(R.id.editTextScore);
        editTextDate = dateEditText;
        buttonSave = findViewById(R.id.saveBtn);

        // Set up text change listeners
        editTextName.addTextChangedListener(textWatcher);
        editTextScore.addTextChangedListener(textWatcher);
        editTextDate.addTextChangedListener(textWatcher);

        // Set up click listener for save button
        buttonSave.setOnClickListener(v -> saveScore());
    }

    // Text change listener for all edit text fields
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Check if all fields are valid
            boolean isValid = isNameValid() && isScoreValid() && isDateValid();
            // Enable/disable save button accordingly
            buttonSave.setEnabled(isValid);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    // Check if name field is valid
    private boolean isNameValid() {
        return !TextUtils.isEmpty(editTextName.getText()) && editTextName.getText().length() <= 25;
    }

    // Check if score field is valid
    private boolean isScoreValid() {
        try {
            int score = Integer.parseInt(editTextScore.getText().toString());
            return score > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Check if date field is valid
    private boolean isDateValid() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            sdf.setLenient(false);
            Date date = sdf.parse(editTextDate.getText().toString());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Save score and return to MainActivity
    private void saveScore() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("name", editTextName.getText().toString());
        resultIntent.putExtra("score", editTextScore.getText().toString());
        resultIntent.putExtra("date", editTextDate.getText().toString());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
