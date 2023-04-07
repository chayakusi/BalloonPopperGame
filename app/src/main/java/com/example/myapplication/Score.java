package com.example.myapplication;

import java.util.Comparator;
import java.util.Date;

public class Score {
    private String name;
    private int score, rank;
    private Date dateTime;

    public Score(String name, int score, Date dateTime) {

        this.name = name;
        this.score = score;
        this.dateTime = dateTime;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public static Comparator<Score> getComparator() {
        return new Comparator<Score>() {
            @Override
            public int compare(Score s1, Score s2) {
                if(s1.getScore() != s2.getScore()) {
                    return Integer.compare(s2.getScore(), s1.getScore());
                }
                return s2.getDateTime().compareTo(s1.getDateTime());
            }
        };
    }
}
