package com.landson.wordsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Model model;

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

        model.generate(0);
        Log.d("Grid", String.valueOf(model.grid));
    }
}