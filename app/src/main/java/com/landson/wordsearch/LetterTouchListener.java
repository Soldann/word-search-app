package com.landson.wordsearch;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LetterTouchListener implements RecyclerView.OnItemTouchListener {
    private LetterAdapter.LetterClickListener letterClickListener;
    int size;

    LetterTouchListener(LetterAdapter.LetterClickListener listener, int size){
        this.letterClickListener = listener;
        this.size = size;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View letter = rv.findChildViewUnder(e.getX(),e.getY());
        int position = rv.getChildAdapterPosition(letter);
        if (letter != null){
            letterClickListener.onLetterTouch(letter,position % size, (position / size) % size, e);
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
