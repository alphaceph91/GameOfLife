package com.example.gameoflife;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GameOfLifeView gameOfLifeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameOfLifeView = (GameOfLifeView) findViewById(R.id.game_of_life);
    }
    @Override
    protected void onResume() {
        super.onResume(); //resuming in usual way but after that the next code

        //data from another app, get intent and get Boolean array by name CELL_DATA
        boolean[] golData = getIntent().getBooleanArrayExtra("CELL_DATA");

        if (golData != null) { //if there is data in our intent
            //set that data in gamaoflife
            //we stored 2d array in 1d by rows
            // to define how many rows (or clomns) was in 2d array we calc sqrt
            gameOfLifeView.setData((int)Math.sqrt(golData.length), (int)Math.sqrt(golData.length), golData);
        } else {
            //if there is no data(CELL_DATA) we show a toast of no data
            Toast.makeText(this, "There is no data from another app", Toast.LENGTH_SHORT).show();
        }

        gameOfLifeView.start(); //start game of life
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameOfLifeView.stop();
    }
}
