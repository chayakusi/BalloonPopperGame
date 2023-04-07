package com.example.myapplication;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class FileIO {
    private static final String file_name = "scores.txt";
    private static final int max_scores = 20;
    static final DateFormat date_format = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    public static Date parseDate(String dateText) {
        try {
            return date_format.parse(dateText);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Score> readScores(Context context) throws IOException {
        List<Score> scores = new ArrayList<>();

        File file = new File(context.getFilesDir(), file_name);
        if(!file.exists()) {
            PrintWriter writer = new PrintWriter(file);
            writer.close();
        }

        int rank = 1;
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(",");

            if(parts.length != 3) {
                throw new IOException("Invalid file format");
            }

            String name = parts[0];
            int value = Integer.parseInt(parts[1]);
            Date date_time = parseDate(parts[2]);

            Score score = new Score(name, value, date_time);
            score.setRank(rank++);
            scores.add(score);
        }
        sc.close();

        Collections.sort(scores, new Comparator<Score>() {
            @Override
            public int compare(Score s1, Score s2) {
                if(s1.getScore() > s2.getScore()) {
                    return -1;
                } else if (s2.getScore() > s1.getScore()) {
                    return 1;
                } else {
                    return s2.getDateTime().compareTo(s1.getDateTime());
                }
            }
        });

        if(scores.size() > max_scores) {
            scores = scores.subList(0, max_scores);
        }
        return scores;
    }
    public static void writeScore(Context context, Score newScore) throws IOException {
        List<Score> scores = readScores(context);
        scores.add(newScore);
        PrintWriter printWriter = null;

        try {
            File file = new File(context.getFilesDir(), file_name);
            printWriter = new PrintWriter(file);

            Collections.sort(scores, new Comparator<Score>() {
                @Override
                public int compare(Score o1, Score o2) {
                    if (o1.getScore() > o2.getScore()) {
                        return -1;
                    } else if (o1.getScore() < o2.getScore()) {
                        return 1;
                    } else {
                        return o2.getDateTime().compareTo(o1.getDateTime());
                    }
                }
            });

            if (scores.size() > max_scores) {
                scores = scores.subList(0, max_scores);
            }

            for (Score score : scores) {
                String line = String.format(Locale.US, "%s%s%d%s%s", score.getName(), ",",
                        score.getScore(), ",", date_format.format(score.getDateTime()));
                printWriter.println(line);
            }
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
