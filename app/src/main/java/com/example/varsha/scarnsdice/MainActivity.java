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
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    int userOverallScore = 0, computerOverallScore = 0;
    int userTurnScore = 0, computerTurnScore = 0;
    TextView labelScore;
    Button rollButton , holdButton;
    ImageView diceView;
    Timer myTimer;

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
            labelText = userScoreLabel + userOverallScore + computerScoreLabel + computerOverallScore + userTurnScoreLabel + userTurnScore + "\n you lost your chance";
            computerTurn();
        } else{
            userTurnScore +=rolledNumber;
            labelText = userScoreLabel + userOverallScore + computerScoreLabel + computerOverallScore + userTurnScoreLabel + userTurnScore;
        }
        labelScore.setText(Html.fromHtml(labelText));
    }

    private void computerTurn() {
        myTimer = new Timer();

        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                //disable the buttons while computer is playing
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        enableButtons(false);
                    }
                });

                //roll dice for comp
                int computerRolledNumber = rollDice();

                //update the image on the ui thread
                final int finalComputerRolledNumber = computerRolledNumber;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        diceView.setImageResource(diceDrawables[finalComputerRolledNumber]);
                    }
                });

                //if computer looses, set turnSCore to 0 and enable buttons for user's turn
                if (computerRolledNumber == 1) {
                    computerTurnScore = 0;
                    labelText = userScoreLabel + userOverallScore + computerScoreLabel + computerOverallScore + userTurnScoreLabel + userTurnScore +
                            "\n computer rolled a one and lost it's chance";


                    //enable buttons, on ui thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enableButtons(true);
                        }
                    });

                    //update the label
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            labelScore.setText(Html.fromHtml(labelText));
                        }
                    });

                    //cancel the timer, this is exiting out of function
                    myTimer.cancel();

                }

                //if not 1, add the score to turn score
                else {
                    computerTurnScore += computerRolledNumber;
                    labelText = userScoreLabel + userOverallScore + computerScoreLabel + computerOverallScore + userTurnScoreLabel + userTurnScore
                            + "\nComputer rolled a " + computerRolledNumber
                            + computerTurnScoreLabel + computerTurnScore;

                    //update the label
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            labelScore.setText(Html.fromHtml(labelText));
                        }
                    });

                    //if the turn score is greater than 20 stop rolling and hold(update the comp score and cancel timer)
                    if (computerTurnScore > 20) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                enableButtons(true);
                            }
                        });

                        computerOverallScore += computerTurnScore;
                        computerTurnScore = 0;
                        labelText = userScoreLabel + userOverallScore + computerScoreLabel + computerOverallScore + "\nComputer holds";

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                labelScore.setText(Html.fromHtml(labelText));
                            }
                        });


                        myTimer.cancel();

                    }
                }
            }
        },0,2000);
    }

    public void holdButtonOnClick(View v){

        userOverallScore += userTurnScore;
        userTurnScore = 0;

        labelText = userScoreLabel + userOverallScore + computerScoreLabel + computerOverallScore + userTurnScoreLabel + userTurnScore;
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
        labelText = userScoreLabel + userOverallScore + computerScoreLabel + computerOverallScore;
        labelScore.setText(Html.fromHtml(labelText));
    }

    private void enableButtons(boolean isEnabled){
        rollButton.setEnabled(isEnabled);
        holdButton.setEnabled(isEnabled);
    }
}
