package com.example.achirafernando.coursework01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TotalGameScore extends AppCompatActivity {

    private int gameScore;
    private TextView gameTotal;
    private  int score;


    public TotalGameScore(int score){

        this.gameScore = score;

    }

    public TotalGameScore(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_game_score);

        Bundle bundle = getIntent().getExtras();

        int score = bundle.getInt("score",404);

        gameTotal = (TextView) findViewById(R.id.totalScore);
        gameTotal.setText(Integer.toString(score));


    }

    public void homeButtonClick(View v){

        finish();
        startActivity(new Intent(getApplicationContext(), MainMenu.class));
    }



}
