package com.example.achirafernando.coursework01;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.CountDownTimer;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.KeyEvent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.Random;


public class GameWindow extends AppCompatActivity {

    private int total;
    private String answer;


    private TextView display;
    private EditText ans;
    private TextView tv;
    private TextView tmr;
    private TextView hintDisplay;

    private String withDashes ="";
    private String displayEquation ="";
    private CountDownTimer timer = null;

    public static int totalGameScore;
    public static int count;
    private static int chances = 0;
    public static String level;
    public static String hints;

    private Button minusBut;
    private Button zeroBut;
    private Button hashBut;

    TotalGameScore tgs;

    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_window);

        Bundle bundle = getIntent().getExtras();
        Boolean continueButPress = bundle.getBoolean("pressedButton", false);

        minusBut = (Button) findViewById(R.id.buttonMinus);
        minusBut.setEnabled(true);

        zeroBut = (Button) findViewById(R.id.zeroButton);
        zeroBut.setEnabled(false);

        hashBut = (Button) findViewById(R.id.buttonHash);
        hashBut.setEnabled(false);

        if(continueButPress){

            tv = (TextView) findViewById(R.id.equation);
            preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);

            level = preferenceSettings.getString("level", " def");
            hints = preferenceSettings.getString("hint", "hint");
            count = preferenceSettings.getInt("count", 0);
            totalGameScore = preferenceSettings.getInt("totalScore", 0);
            displayEquation = preferenceSettings.getString("equation", "def");
            withDashes = preferenceSettings.getString("withDashes", "dashes ");

            tv.setText(displayEquation);


        }else{

            level = bundle.getString("level");
            hints = bundle.getString("hint"," ");

            CreateEquation(SymbolNumber(level));


        }


        tmr = (TextView) findViewById(R.id.timer);
        test = (TextView) findViewById(R.id.test);
        hintDisplay = (TextView) findViewById(R.id.help);

        chances = 0;

        reverseTimer(10, tmr);

        calculateAnswers();


       // if(hints == "on"){
        //    Toast.makeText(getApplicationContext(), "Hints on", Toast.LENGTH_LONG).show();
      //  }

      //  hints = "on";
        
    }

    public void toDo(){
        reverseTimer(10, tmr);

        SymbolNumber(level);

        calculateAnswers();
    }

    public void reverseTimer(int Seconds,final TextView tv){

        timer = new CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                seconds = seconds % 60;
                tv.setText(getTwoDigits(seconds));
            }

            private String getTwoDigits(int value) {
                if ((value < 10)) {
                    return "0" + value;
                }
                return Integer.toString(value);
            }


            public void onFinish() {

                tv.setText("10");

                if(hints.equals("on")){

                    if(count < 10) {

                        if(chances >= 4){

                            count++;
                            finish();
                            startActivity(getIntent());

                            Toast.makeText(getApplicationContext(), Integer.toString(count), Toast.LENGTH_LONG).show();



                        }else{

                            chances++;

                            timer.cancel();
                            timer.start();

                        }


                    }else {

                        timer.cancel();
                        showTotalGameScore(totalGameScore);
                        Toast.makeText(getApplicationContext(), "Score : "+Integer.toString(count), Toast.LENGTH_LONG).show();

                    }


                }else{

                    if(count < 10) {

                        count++;
                        Toast.makeText(getApplicationContext(), Integer.toString(count), Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());


                    }else {

                        timer.cancel();
                        showTotalGameScore(totalGameScore);
                        Toast.makeText(getApplicationContext(), "Score : "+Integer.toString(totalGameScore), Toast.LENGTH_LONG).show();

                    }

                }



            }

        }.start();
    }

    public int SymbolNumber(String level){

        int numberOfSymbols = 0;

        if(level.equals("novice")){
            numberOfSymbols = 2;                        //2 integers
        }
        else if(level.equals("easy")){
            numberOfSymbols = randomSymbols(2, 3);      //2 or 3 integers
        }
        else if(level.equals("medium")){
            numberOfSymbols = randomSymbols(2, 4);      //2,3 or 4 integers
        }
        else if(level.equals("guru")){
            numberOfSymbols = randomSymbols(4, 6);      //4,5 or  6 integers
        }

        return numberOfSymbols;



        //return numberOfSymbols;

    }

    public String CreateSymbol(int num){

        String symbol = "";

        if(num == 1){
            symbol = "+";
        }else if(num ==2){
            symbol = "-";
        }else if(num ==3){
            symbol = "*";
        }else if(num ==4){
            symbol ="/";
        }

        return symbol;
    }

    public int createSymbolNumber(String symbol){
        int num = 0;

        if(symbol == "+"){
            num = 1;
        }else if(symbol == "-"){
            num = 2;
        }else if(symbol == "*"){
            num = 3;
        }else if(symbol == "/"){
            num = 4;
        }

        return num;
    }

    public void CreateEquation(int symbolNumber){

        String createSymbol ;
        String randomNumber ;

        for(int i=0; i<symbolNumber; i++) {

            createSymbol = CreateSymbol(randomSymbols(1,4));
            randomNumber = Integer.toString(randomGenerator());

            if (i==0) {
                displayEquation = Integer.toString(randomGenerator());
                withDashes = displayEquation;
            }else{
                displayEquation = displayEquation + createSymbol + randomNumber;
                withDashes = withDashes + "-" + createSymbolNumber(createSymbol) + "-" + randomNumber;
            }

        }

        tv = (TextView) findViewById(R.id.equation);
        tv.setText(displayEquation);


    }

    public int calculateAnswers(){

        String[] numArr = withDashes.split("-");

        for(int i = 1; i < numArr.length; i = i+2){

            if(i == 1){
                if(numArr[i].equals("1")){
                    total = Integer.parseInt(numArr[i-1]) + Integer.parseInt(numArr[i+1]);
                }
                else if(numArr[i].equals("2")){
                    total = Integer.parseInt(numArr[i-1]) - Integer.parseInt(numArr[i+1]);
                }
                else if(numArr[i].equals("3")){
                    total = Integer.parseInt(numArr[i-1]) * Integer.parseInt(numArr[i+1]);
                }
                else if(numArr[i].equals("4")){
                    total = Integer.parseInt(numArr[i-1]) / Integer.parseInt(numArr[i+1]);
                }
            }else{

                if(numArr[i].equals("1")){
                    total = total + Integer.parseInt(numArr[i+1]);
                }
                else if(numArr[i].equals("2")){
                    total = total - Integer.parseInt(numArr[i+1]);
                }
                else if(numArr[i].equals("3")){
                    total = total * Integer.parseInt(numArr[i+1]);
                }
                else if(numArr[i].equals("4")){
                    total = total / Integer.parseInt(numArr[i+1]);
                }

            }
        }

        // ans = (EditText) findViewById(R.id.answer);
        // ans.setText(Integer.toString(total));

        return total;


    }

    public int randomGenerator(){

        Random rand = new Random();
        int randomNum = rand.nextInt(100 - 1 + 1) + 1;

        return randomNum;
    }

    public int randomSymbols(int min, int max){

        Random rand = new Random();
        int randomNum = rand.nextInt(max - min + 1) + min;

        return randomNum;
    }

   private String rot = "";

    public void OnClick(View v){

        Button but = (Button)v;
        String str = but.getText().toString();
        rot += str;
        EditText ans = (EditText) findViewById(R.id.answer);

        ans.setText(rot);
        zeroBut.setEnabled(true);
        hashBut.setEnabled(true);
        minusBut.setEnabled(false);


    }

    public void zeroClick(View v){

        zeroBut = (Button) findViewById(R.id.zeroButton);

        String str = "0";
        rot += str;
        EditText ans = (EditText) findViewById(R.id.answer);
        ans.setText(rot);

       // zeroBut.setEnabled(true);
    }

    public void minusClick(View v){

        minusBut = (Button) findViewById(R.id.buttonMinus);

        String str = "-";
        rot += str;
        EditText ans = (EditText) findViewById(R.id.answer);
        ans.setText(rot);

        minusBut.setEnabled(false);

    }



    public void clickDel(View v){

        minusBut.setEnabled(true);
        zeroBut.setEnabled(false);
        hashBut.setEnabled(false);

        ((EditText) findViewById(R.id.answer)).setText("");
        this.rot = "";

    }


    public void clickHash(View v){

        ans = (EditText) findViewById(R.id.answer);
        answer = ans.getText().toString();

        hintDisplay = (TextView) findViewById(R.id.help);

        display = (TextView) findViewById(R.id.correctWrong);

        if(hints.equals("on")){

            if(count < 10) {

                if( total == Integer.parseInt(answer)){
                    //display.setTextColor(0xFF0000);
                    display.setText("CORRECT!");

                    count++;

                    // timer.cancel();
                    totalGameScore = totalGameScore + generateScore(Integer.parseInt(tmr.getText().toString()));

                    finish();
                    startActivity(getIntent());

                    Toast.makeText(getApplicationContext(), Integer.toString(count), Toast.LENGTH_LONG).show();


                }else{

                    if(chances < 4) {

                        if (total == Integer.parseInt(answer)) {
                            count++;

                            timer.cancel();
                            finish();
                            startActivity(getIntent());

                            Toast.makeText(getApplicationContext(), Integer.toString(count), Toast.LENGTH_LONG).show();

                            //break;

                        } else {

                            timer.cancel();
                            timer.start();

                            if (total > Integer.parseInt(answer)) {
                                hintDisplay.setText(">");
                            } else {
                                hintDisplay.setText("<");
                            }

                        }

                        chances++;
                    }else{

                        count++;

                        timer.cancel();
                        finish();
                        startActivity(getIntent());

                        Toast.makeText(getApplicationContext(), Integer.toString(count), Toast.LENGTH_LONG).show();

                    }

                    // test.setText(Integer.toString(chances));

                    // chances = 0;

                }

            }else{

                timer.cancel();
                showTotalGameScore(totalGameScore);
                Toast.makeText(getApplicationContext(), "Score : " +Integer.toString(totalGameScore), Toast.LENGTH_LONG).show();

            }



        }else{
            if(count < 10) {
                if( total == Integer.parseInt(answer)){
                    //display.setTextColor(0xFF0000);
                    display.setText("CORRECT!");

                    count++;

                    timer.cancel();
                    Toast.makeText(getApplicationContext(), Integer.toString(count), Toast.LENGTH_LONG).show();
                    totalGameScore = totalGameScore + generateScore(Integer.parseInt(tmr.getText().toString()));

                    finish();
                    startActivity(getIntent());

                }else{

                    count++;

                    timer.cancel();
                    display.setText("WRONG!");

                    Toast.makeText(getApplicationContext(), Integer.toString(count), Toast.LENGTH_LONG).show();

                    finish();
                    startActivity(getIntent());


                }
            }else{
                timer.cancel();
                showTotalGameScore(totalGameScore);
                Toast.makeText(getApplicationContext(), "Score : " +Integer.toString(totalGameScore), Toast.LENGTH_LONG).show();
            }


        }


      //  test.setText(Integer.toString(totalGameScore));


    }

    public int generateScore(int timeRemaining){

        int score = 100/(10-timeRemaining);

        return score;


    }

    public void onPause(){

        super.onPause();

        saveDetails();
        chances = 0;
        timer.cancel();

    }

    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;

    public static final int PREFERENCE_MODE_PRIVATE = 0;

    public void saveDetails(){

        int cnt = this.count;
        int gameScore = this.totalGameScore;
        String gameLevel = this.level;
        String hintS = this.hints;
        String equation = this.displayEquation;
        String answer = this.withDashes;

        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        preferenceEditor.putInt("count", cnt);
        preferenceEditor.putInt("totalScore", gameScore);
        preferenceEditor.putString("level", gameLevel);
        preferenceEditor.putString("hint", hintS);
        preferenceEditor.putString("equation", equation);
        preferenceEditor.putString("withDashes", answer);
        preferenceEditor.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    public void showTotalGameScore(int score){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(GameWindow.this);
        alertDialog.setMessage("TOTAL GAME SCORE : " + score);
        alertDialog.setPositiveButton("Return to main menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainMenu.class));

            }
        })
                .setNegativeButton("New game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(new Intent(getApplicationContext(), LevelChooser.class));
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.setTitle("Game Over");
        alert.setIcon(R.drawable.ex);
        alert.show();


    }


}
