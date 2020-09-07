package com.landson.wordsearch;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.ViewHolder> {

    private ArrayList<ArrayList<Character>> grid;
    LetterAdapter(ArrayList<ArrayList<Character>> g) { this.grid = g; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new ViewHolder(inflater.inflate(R.layout.grid_letter_layout,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character letter = grid.get((position / grid.size()) % grid.size()).get(position % grid.size());
        if (letter == null){
            holder.letter.setText(" ");
        } else {
            holder.letter.setText(letter.toString());
        }

    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (ArrayList<Character> row : grid){
            count += row.size();
        }
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView letter;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            letter = itemView.findViewById(R.id.letter);
        }
    }
}
