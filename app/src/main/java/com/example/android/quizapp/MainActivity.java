package com.example.android.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String candidateName;
    private int scoreCount;
    private int totalQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method creates a summary of the order.
     * <p>
     * param view of the order
     * return text summary
     */
    public void registerCandidate(View view) {
        TextView nameView = findViewById(R.id.nameTextView);
        candidateName = nameView.getText().toString();
        if (candidateName.trim().length() > 0) {
            TextView candidateView = findViewById(R.id.candidateTextView);
            LinearLayout registerLayout = findViewById(R.id.registerLayout);
            LinearLayout questionsLayout = findViewById(R.id.questionsGroup);
            RelativeLayout summaryLayout = findViewById(R.id.summaryLayout);
            candidateView.setText(String.format(getString(R.string.candidate_playing), candidateName));
            registerLayout.setVisibility(View.GONE);
            candidateView.setVisibility(View.VISIBLE);
            questionsLayout.setVisibility(View.VISIBLE);
            summaryLayout.setVisibility(View.VISIBLE);
        } else
            Toast.makeText(this, "Please register your NAME to continue.", Toast.LENGTH_SHORT).show();
    }

    public void restartApp(View view) {
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void submitAnswers(View view) {
        RadioGroup radioGroupQ1 = findViewById(R.id.radioGroupQ1);
        int rightAnswerQ1 = R.id.optionQ1_b;
        RadioGroup radioGroupQ2 = findViewById(R.id.radioGroupQ2);
        int rightAnswerQ2 = R.id.optionQ2_b;
        RadioGroup radioGroupQ3 = findViewById(R.id.radioGroupQ3);
        int rightAnswerQ3 = R.id.optionQ3_a;
        RadioGroup radioGroupQ7 = findViewById(R.id.radioGroupQ7);
        int rightAnswerQ7 = R.id.optionQ7_a;
        RadioGroup radioGroupQ8 = findViewById(R.id.radioGroupQ8);
        int rightAnswerQ8 = R.id.optionQ8_c;

        EditText candidateAnswerTViewQ5 = findViewById(R.id.optionQ5_a);
        String rightAnswerQ5 = getResources().getString(R.string.q5_option_a);

        CheckBox rightAnswer1Q4 = findViewById(R.id.optionQ4_a);
        CheckBox rightAnswer2Q4 = findViewById(R.id.optionQ4_d);
        CheckBox wrongAnswer1Q4 = findViewById(R.id.optionQ4_b);
        CheckBox wrongAnswer2Q4 = findViewById(R.id.optionQ4_c);
        CheckBox rightAnswer1Q6 = findViewById(R.id.optionQ6_a);
        CheckBox rightAnswer2Q6 = findViewById(R.id.optionQ6_c);
        CheckBox wrongAnswer1Q6 = findViewById(R.id.optionQ6_b);
        CheckBox wrongAnswer2Q6 = findViewById(R.id.optionQ6_d);

        gradeRadioQuestions(radioGroupQ1, rightAnswerQ1);
        gradeRadioQuestions(radioGroupQ2, rightAnswerQ2);
        gradeRadioQuestions(radioGroupQ3, rightAnswerQ3);
        gradeRadioQuestions(radioGroupQ7, rightAnswerQ7);
        gradeRadioQuestions(radioGroupQ8, rightAnswerQ8);

        gradeTextQuestions(candidateAnswerTViewQ5, rightAnswerQ5);

        gradeCheckQuestions(rightAnswer1Q4, rightAnswer2Q4, wrongAnswer1Q4, wrongAnswer2Q4);
        gradeCheckQuestions(rightAnswer1Q6, rightAnswer2Q6, wrongAnswer1Q6, wrongAnswer2Q6);

        Toast.makeText(this, "You got " + scoreCount + " ANSWERS RIGHT out of " + totalQuestions + " ATTEMPTED questions", Toast.LENGTH_SHORT).show();
        scoreCount = 0;
        totalQuestions = 0;
    }

    public void gradeTextQuestions(EditText candidateAnswerTView, String rightAnswer) {
        String candidateAnswer = candidateAnswerTView.getText().toString();
        if (candidateAnswer.trim().length() > 0) {
            if (candidateAnswer.equals(rightAnswer)) {
                scoreCount += 1;
                totalQuestions += 1;
            } else {
                totalQuestions += 1;
                candidateAnswerTView.setText(rightAnswer);
                candidateAnswerTView.setTextColor(getResources().getColor(R.color.colorPrimaryText));
            }
        } else {
//            Toast.makeText(this, "Please answer all questions TEXT.", Toast.LENGTH_SHORT).show();
//            return;
        }
    }

    public void gradeCheckQuestions(CheckBox rightAnswer1Q4, CheckBox rightAnswer2Q4, CheckBox wrongAnswer1Q4, CheckBox wrongAnswer2Q4) {
        if (wrongAnswer1Q4.isChecked() || wrongAnswer2Q4.isChecked()) {
            totalQuestions += 1;
        } else if (rightAnswer1Q4.isChecked() && rightAnswer2Q4.isChecked()) {
            scoreCount += 1;
            totalQuestions += 1;
        } else {
//            Toast.makeText(this, "Please answer all questions CHECK.", Toast.LENGTH_SHORT).show();
        }

        wrongAnswer1Q4.setChecked(false);
        wrongAnswer2Q4.setChecked(false);
        rightAnswer1Q4.setChecked(true);
        rightAnswer2Q4.setChecked(true);
        rightAnswer1Q4.setTextColor(getResources().getColor(R.color.colorPrimaryText));
        rightAnswer2Q4.setTextColor(getResources().getColor(R.color.colorPrimaryText));
    }

    public void gradeRadioQuestions(RadioGroup radioGroup, int rightAnswer) {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
//            Toast.makeText(this, "Please answer all questions RADIO.", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton rightAnswerRadioButton = radioGroup.findViewById(rightAnswer);
            int yourAnswerRadioButtonID = radioGroup.getCheckedRadioButtonId();
            RadioButton yourAnswerRadioButton = radioGroup.findViewById(yourAnswerRadioButtonID);
            if (yourAnswerRadioButton.getText().toString().equals(rightAnswerRadioButton.getText().toString())) {
                scoreCount += 1;
            }
            totalQuestions += 1;
            yourAnswerRadioButton.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
            rightAnswerRadioButton.setChecked(true);
            rightAnswerRadioButton.setTextColor(getResources().getColor(R.color.colorPrimaryText));
        }
    }
}