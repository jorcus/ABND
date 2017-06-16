package com.example.kiang.quizapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.kiang.quizapp.R.id.Q2AnsCheckbox1;
import static com.example.kiang.quizapp.R.id.Q2AnsCheckbox2;
import static com.example.kiang.quizapp.R.id.Q2AnsCheckbox3;
import static com.example.kiang.quizapp.R.id.Q2AnsCheckbox4;
import static com.example.kiang.quizapp.R.id.Q2AnsCheckbox5;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submit(View view) {

        /* Question 1 */
        RadioButton Q1AnsCheck = (RadioButton) findViewById(R.id.false_correct);
        boolean Q1Ans = Q1AnsCheck.isChecked();

         /* Question 2 */
        CheckBox Q2AnsCbox1 = (CheckBox) findViewById(Q2AnsCheckbox1);
        boolean Q2AnsC1 = Q2AnsCbox1.isChecked();
        CheckBox Q2AnsCbox2 = (CheckBox) findViewById(Q2AnsCheckbox2);
        boolean Q2AnsC2 = Q2AnsCbox2.isChecked();
        CheckBox Q2AnsCbox3 = (CheckBox) findViewById(Q2AnsCheckbox3);
        boolean Q2AnsC3 = Q2AnsCbox3.isChecked();
        CheckBox Q2AnsCbox4 = (CheckBox) findViewById(Q2AnsCheckbox4);
        boolean Q2AnsC4 = Q2AnsCbox4.isChecked();
        CheckBox Q2AnsCbox5 = (CheckBox) findViewById(Q2AnsCheckbox5);
        boolean Q2AnsC5 = Q2AnsCbox5.isChecked();

         /* Question 3 */
        EditText answerField = (EditText) findViewById(R.id.Q3Ans);
        Editable answerEditable = answerField.getText();
        String answer = answerEditable.toString();

         /* Question 4 */
        RadioButton Q4AnsCheck = (RadioButton) findViewById(R.id.Q4Ans);
        boolean Q4Ans = Q4AnsCheck.isChecked();

        int score = calculateScore(Q1Ans, Q2AnsC1, Q2AnsC2, Q2AnsC3, Q2AnsC4, Q2AnsC5, Q4Ans) + fillerAnswer(answer);

        String result = "Your score is " + score + " out of 4";
        displayResult(result);
    }

    private int calculateScore(boolean Q1Ans, boolean Q2AnsC1, boolean Q2AnsC2, boolean Q2AnsC3, boolean Q2AnsC4, boolean Q2AnsC5, boolean Q4Ans) {
        int baseScore = 0;
        if (Q1Ans) {
            baseScore++;
        } else {
            baseScore = baseScore;
        }

        if (Q2AnsC1 && Q2AnsC2 && Q2AnsC3 && Q2AnsC4 && Q2AnsC5) {
            baseScore++;
        } else {
            baseScore = baseScore;
        }

        if (Q4Ans) {
            baseScore++;
        } else {
            baseScore = baseScore;
        }

        return baseScore;

    }

    private int fillerAnswer(String answer) {
        int baseScore = 0;
        if (answer.equalsIgnoreCase("1995")) {
            baseScore++;
        } else {
            baseScore = baseScore;
        }
        return baseScore;
    }

    public void displayResult(String message) {

        //get text from button
        TextView result = (TextView) findViewById(R.id.result);
        Context context = getApplicationContext();

        //make toast and show it
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}

