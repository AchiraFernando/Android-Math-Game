package com.example.achirafernando.coursework01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class LevelChooser extends AppCompatActivity {

    private boolean switched = false;

    private Switch hint;
    private TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_chooser);

       hint = (Switch)findViewById(R.id.hintSwitch);
        txt  = (TextView) findViewById(R.id.status);

        GameWindow gm = new GameWindow();
        gm.count = 0;

        hint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    switched = true;
                    txt.setText("on");

                }else{
                    txt.setText("off");
                }
            }
        });

    }

    private String lvl;
    private String hints = "on";


    public void noviceClick(View v){

        Intent sendStuff = new Intent(this, GameWindow.class);
        Bundle bundle = new Bundle();
        this.lvl = "novice";

        if(switched == true){

            bundle.putString("level", lvl);
            bundle.putString("hint", "on");
            sendStuff.putExtras(bundle);
            startActivity(sendStuff);

        }else{

            bundle.putString("level", lvl);
            sendStuff.putExtras(bundle);
            startActivity(sendStuff);

        }

    }

    public void easyClick(View v){

        Intent sendStuff = new Intent(this, GameWindow.class);
        Bundle bundle = new Bundle();
        this.lvl = "easy";

        if(switched == true){

            bundle.putString("level", lvl);
            bundle.putString("hint", hints);
            sendStuff.putExtras(bundle);
            startActivity(sendStuff);

        }else {

            bundle.putString("level", lvl);
            sendStuff.putExtras(bundle);
            startActivity(sendStuff);

        }

    }

    public void mediumClick(View v){

        Intent sendStuff = new Intent(this, GameWindow.class);
        Bundle bundle = new Bundle();
        this.lvl = "medium";

        if(switched == true){

            bundle.putString("level", lvl);
            bundle.putString("hint", hints);
            sendStuff.putExtras(bundle);
            startActivity(sendStuff);

        }else {

            bundle.putString("level", lvl);
            sendStuff.putExtras(bundle);
            startActivity(sendStuff);

        }

    }

    public void guruClick(View v){

        Intent sendStuff = new Intent(this, GameWindow.class);
        Bundle bundle = new Bundle();
        this.lvl = "guru";

        if(switched == true){

            bundle.putString("level", lvl);
            bundle.putString("hint", hints);
            sendStuff.putExtras(bundle);
            startActivity(sendStuff);

        }else {

            bundle.putString("level", lvl);
            sendStuff.putExtras(bundle);
            startActivity(sendStuff);

        }






    }



}
