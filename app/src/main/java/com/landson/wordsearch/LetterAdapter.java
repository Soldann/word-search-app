package com.landson.wordsearch;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.ViewHolder> {

    private ArrayList<ArrayList<Character>> grid;
    private ArrayList<ArrayList<Direction>> selectionArray;
    private boolean[] selected;
    private RecyclerView rv;
    LetterAdapter(ArrayList<ArrayList<Character>> g, ArrayList<ArrayList<Direction>> selectionArray, RecyclerView rv) {
        this.grid = g;
        this.selectionArray = selectionArray;
        this.rv = rv;
        selected = new boolean[100];
    }

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

        if (selected[position]){
            holder.letter.setBackgroundColor(Color.RED);
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

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        rv = recyclerView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView letter;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            letter = itemView.findViewById(R.id.letter);
        }

    }

    public int getPositionFromCoordinate(int x, int y){
        return x + y * grid.size();
    }

    public interface LetterClickListener {
        void onLetterTouch(View v, int positionX, int positionY, MotionEvent event);
    }

    public void reload(){
        //colour all selected red (resource intensive)
        boolean[] oldSelected = selected.clone();
        selected = new boolean[100];
        for (ArrayList<Direction> selection : selectionArray){
            for (Direction letter : selection){
                selected[getPositionFromCoordinate(letter.x,letter.y)] = true;
            }
        }

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilCalculator(grid,oldSelected,selected));
        diffResult.dispatchUpdatesTo(this);

    }

}
