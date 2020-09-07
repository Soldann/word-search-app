package com.landson.wordsearch;

import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;

public class DiffUtilCalculator extends DiffUtil.Callback {
    private ArrayList<ArrayList<Character>> grid;
    private boolean[] oldSelected;
    private boolean[] newSelected;

    public DiffUtilCalculator(ArrayList<ArrayList<Character>> grid, boolean[] oldSelected, boolean[] newSelected){
        this.grid = grid;
        this.oldSelected = oldSelected;
        this.newSelected = newSelected;
    }


    @Override
    public int getOldListSize() {
        int count = 0;
        for (ArrayList<Character> row : grid){
            count += row.size();
        }
        return count;
    }

    @Override
    public int getNewListSize() {
        int count = 0;
        for (ArrayList<Character> row : grid){
            count += row.size();
        }
        return count;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Direction oldDirection = convertPositionToDirection(oldItemPosition);
        Direction newDirection = convertPositionToDirection(newItemPosition);

        return grid.get(oldDirection.y).get(oldDirection.x) == grid.get(newDirection.y).get(newDirection.x);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldSelected[oldItemPosition] == newSelected[newItemPosition];
    }

    private Direction convertPositionToDirection(int position){
        return new Direction(position % grid.size(), (position / grid.size()) % grid.size());
    }
}
