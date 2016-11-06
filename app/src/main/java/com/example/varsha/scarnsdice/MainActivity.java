package com.example.varsha.scarnsdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int userOverallScore = 0, computerOverallScore = 0;
    int userTurnScore = 0, computerTurnScore = 0;
    TextView labelScore;
    Button rollButton , holdButton;
    ImageView diceView;
    EditText ed;

    String userScoreLabel = "<b><i> Your score : </i></b>";
    String computerScoreLabel = "<b><i> Computer score : </i></b>";
    String userTurnScoreLabel = "<b><i> User Turn score : </i></b>";
    String computerTurnScoreLabel = "<b><i> Computer Turn score : </i></b>";
    String labelText = userScoreLabel + userOverallScore + computerTurnScoreLabel + computerOverallScore;
    String newLine = "\n";

    int[] diceDrawables = {R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollButton = (Button) findViewById(R.id.roll);
        holdButton = (Button) findViewById(R.id.hold);

        labelScore = (TextView) findViewById(R.id.label);
        diceView = (ImageView) findViewById(R.id.diceImage);

        labelScore.setText(Html.fromHtml(labelText));

    }

    public void rollButtonOnClick(View view){
        int rolledNumber = rollDice();

        diceView.setImageResource(diceDrawables[rolledNumber]);
        rolledNumber++; // the array contains the index from 0-5

        if(rolledNumber == 1){
            userTurnScore = 0;
            labelText = userScoreLabel + userOverallScore +newLine+ computerScoreLabel + computerOverallScore + newLine + computerTurnScoreLabel + computerTurnScore + newLine +userTurnScoreLabel + userTurnScore;
            computerTurn();
        } else{
            userTurnScore +=rolledNumber;
            labelText = userScoreLabel + userOverallScore +newLine+ computerScoreLabel + computerOverallScore + newLine + computerTurnScoreLabel + computerTurnScore + newLine +userTurnScoreLabel + userTurnScore;
        }
        labelScore.setText(Html.fromHtml(labelText));
    }

    private void computerTurn() {
    }

    public void holdButtonOnClick(View v){

        userOverallScore += userTurnScore;
        userTurnScore = 0;

        labelText = userScoreLabel + userOverallScore +newLine+ computerScoreLabel + computerOverallScore + newLine + computerTurnScoreLabel + computerTurnScore + newLine +userTurnScoreLabel + userTurnScore;
        labelScore.setText(Html.fromHtml(labelText));

        computerTurn();

    }

    private int rollDice(){
        Random random = new Random();

        int randonNumber = random.nextInt(6); // Random number between 0-5
        Log.d("Random number", String.valueOf(randonNumber));
        return  randonNumber;
    }

    public void resetButtonOnlick(View view) {
        userOverallScore=0;
        userTurnScore=0;
        computerTurnScore=0;
        computerOverallScore=0;
        labelText = userScoreLabel + userOverallScore + newLine + computerScoreLabel + computerOverallScore;
        labelScore.setText(Html.fromHtml(labelText));
    }
}
