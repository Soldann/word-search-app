package com.landson.wordsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements LetterAdapter.LetterClickListener {

    Model model;
    LetterAdapter letterAdapter;
    RecyclerView wordGrid;

    Direction selectionStart; //holds beginning of any touch selection
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new ViewModelProvider(this).get(Model.class);

        model.addWord("swift");
        model.addWord("kotlin");
        model.addWord("objectivec");
        model.addWord("variable");
        model.addWord("java");
        model.addWord("mobile");

        if (model.generate()){
            Log.d("Generator","SUCCESS");
        } else {
            Log.d("Generator","FAILURE");
        }

        Log.d("Grid", String.valueOf(model.grid));

        wordGrid = findViewById(R.id.wordGrid);
        wordGrid.setLayoutManager(new GridLayoutManager(this,model.size));
        letterAdapter  = new LetterAdapter(model.grid, model.selectionArray, wordGrid);
        wordGrid.addOnItemTouchListener(new LetterTouchListener(this,model.size));
        wordGrid.setAdapter(letterAdapter);
    }

    @Override
    public void onLetterTouch(View v, int positionX, int positionY, MotionEvent event) {
        boolean reloadNecessary = false;
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("touch", "start position is x " + positionX + " and y " + positionY);
            model.startSelection(positionX,positionY);
            reloadNecessary = true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE){
            Log.d("touch", "Moved to x " + positionX + " and y " + positionY);
            reloadNecessary = model.setLastSelectionEnd(positionX,positionY);
        } else if (event.getAction() == MotionEvent.ACTION_UP){
            Log.d("touch", "reset");
            reloadNecessary = model.cancelSelection();
        }

        if (reloadNecessary){
            letterAdapter.reload();
        }

    }
}