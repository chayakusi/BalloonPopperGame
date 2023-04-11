/*
* HighScores Android App - By Chaya Kusi for CS6326 Assignment 4 ,starting on April 4, 2023.
* NET ID: CXK210030
* FileIO class for handling File IO
 */
package com.example.myapplication;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class FileIO {
    private static final String file_name = "scores.txt";
    private static final int max_scores = 20;
    static final DateFormat date_format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    // Function to parse Date as a string to DateTime
    public static Date parseDate(String dateText) {
        try {
            return date_format.parse(dateText);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // Function to read scores from the file and return scores
    public static List<Score> readScores(Context context) throws IOException {
        List<Score> scores = new ArrayList<>();

        File file = new File(context.getFilesDir(), file_name);
        if(!file.exists()) {
            PrintWriter writer = new PrintWriter(file);
            writer.close();
        }

        int rank = 1;
        Scanner sc = new Scanner(file);
        // Read each line from the file
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            // Split based on the delimiter
            String[] parts = line.split(",");

            if(parts.length != 3) {
                throw new IOException("Invalid file format");
            }

            // Get  name, score, and dateTime
            String name = parts[0];
            int value = Integer.parseInt(parts[1]);
            Date date_time = parseDate(parts[2]);

            // Create a new Score object
            Score score = new Score(name, value, date_time);
            // Add rank to the object
            score.setRank(rank++);
            // Add the score to scores list
            scores.add(score);
        }
        sc.close();

        // Sort the scores list to get top 20 scores
        scores.sort((s1, s2) -> {
            if (s1.getScore() > s2.getScore()) {
                return -1;
            } else if (s2.getScore() > s1.getScore()) {
                return 1;
            } else {
                return s2.getDateTime().compareTo(s1.getDateTime());
            }
        });

        // Get only the top 20
        if(scores.size() > max_scores) {
            scores = scores.subList(0, max_scores);
        }
        return scores;
    }

    // Function to write the new score into the file, sort, and keep the top 20 scores
    public static void writeScore(Context context, Score newScore) throws IOException {
        // First read the scores
        List<Score> scores = readScores(context);
        // Add the new score to scores list
        scores.add(newScore);
        PrintWriter printWriter = null;

        try {
            File file = new File(context.getFilesDir(), file_name);
            printWriter = new PrintWriter(file);

            // Sort the new scores list
            scores.sort((o1, o2) -> {
                if (o1.getScore() > o2.getScore()) {
                    return -1;
                } else if (o1.getScore() < o2.getScore()) {
                    return 1;
                } else {
                    return o2.getDateTime().compareTo(o1.getDateTime());
                }
            });

            // Keep the top 20 scores
            if (scores.size() > max_scores) {
                scores = scores.subList(0, max_scores);
            }

            // Write the updated 20 scores into the file
            for (Score score : scores) {
                String line = String.format(Locale.US, "%s%s%d%s%s", score.getName(), ",",
                        score.getScore(), ",", date_format.format(score.getDateTime()));
                printWriter.println(line);
            }
        } finally {
            if (printWriter != null) {
                // Close the file
                printWriter.close();
            }
        }
    }
}
