package com.landson.wordsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    Model model;
    LetterAdapter letterAdapter;

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

        RecyclerView wordGrid = findViewById(R.id.wordGrid);
        wordGrid.setLayoutManager(new GridLayoutManager(this,model.size));
        letterAdapter  = new LetterAdapter(model.grid);
        wordGrid.setAdapter(letterAdapter);

    }
}