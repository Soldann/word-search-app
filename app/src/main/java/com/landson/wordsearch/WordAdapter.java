package com.landson.wordsearch;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    ArrayList<Word> words;

    WordAdapter(ArrayList<Word> words){
        this.words = words;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new WordAdapter.ViewHolder(inflater.inflate(R.layout.word_display,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.word.setText(words.get(position).word);

        if (words.get(position).found){
            holder.word.setBackgroundColor(Color.RED);
        } else {
            holder.word.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView word;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            word = itemView.findViewById(R.id.wordDisplay);
        }
    }
}
