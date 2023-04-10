package com.example.myapplication;

import static com.example.myapplication.FileIO.date_format;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreViewHolder> {
    Context context;
    List<Score> scores;

    public ScoreAdapter(Context context, List<Score> scores) {
        this.context = context;
        this.scores = scores;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScoreViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = scores.get(position);
        holder.rankView.setText(String.valueOf(score.getRank()));
        holder.nameView.setText(score.getName());
        holder.scoreView.setText(String.valueOf(score.getScore()));
        holder.dateView.setText(date_format.format(score.getDateTime()));
    }

    @Override
    public int getItemCount() {
        if(scores == null) {
            return 0;
        }
        return scores.size();
    }
}
