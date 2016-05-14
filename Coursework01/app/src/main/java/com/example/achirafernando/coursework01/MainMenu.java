package com.example.achirafernando.coursework01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;

    GameWindow gw;

    public static final int PREFERENCE_MODE_PRIVATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void newGame(View v){

        startActivity(new Intent(getApplicationContext(), LevelChooser.class));

    }

    public void continueButton(View v){

        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);

        int count = preferenceSettings.getInt("count", 1);
        int totalScore = preferenceSettings.getInt("totalScore", 2);
        String level = preferenceSettings.getString("level", "no String");
        String hint = preferenceSettings.getString("hint", " ");
        String equatioN = preferenceSettings.getString("equation", " ");
        String withDasheS = preferenceSettings.getString("withDashes", " ");
        Boolean buttonPressed = true;

        Intent sendStuff = new Intent(this, GameWindow.class);
        Bundle bundle = new Bundle();

        bundle.putString("level", level);
        bundle.putInt("totalScore", totalScore);
        bundle.putInt("count", count);
        bundle.putString("hint", hint);
        bundle.putString("equation", equatioN);
        bundle.putString("withDashes", withDasheS);
        bundle.putBoolean("pressedButton", buttonPressed);

        sendStuff.putExtras(bundle);
        startActivity(sendStuff);


    }

    public void exitButton(View v) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainMenu.this);
        alertDialog.setMessage("Do you want to save before exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

             /*   gw = new GameWindow();

                int count = gw.count;
                int totalScore = gw.totalGameScore;
                String gameLevel = gw.level;
                String hint = gw.hints;

                preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
                preferenceEditor = preferenceSettings.edit();


                preferenceEditor.putInt("count", count);
                preferenceEditor.putInt("totalScore", totalScore);
                preferenceEditor.putString("level", gameLevel);
                preferenceEditor.putString("hint", hint);
                preferenceEditor.commit();

             */

                        Toast.makeText(getApplicationContext(), "Pressed Yes", Toast.LENGTH_LONG).show();
                    }
        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                        Toast.makeText(getApplicationContext(), "Pressed No", Toast.LENGTH_LONG).show();
                    }
        });

        AlertDialog alert = alertDialog.create();
        alert.setTitle("Confirm Exit");
        alert.setIcon(R.drawable.ex);
        alert.show();


    }

    public void aboutButton(View v){

        startActivity(new Intent(getApplicationContext(), AboutGame.class));

    }

}
