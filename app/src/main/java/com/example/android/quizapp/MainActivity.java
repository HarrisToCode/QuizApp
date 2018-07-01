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

    // variable holding the candidate name
    private String candidateName;
    // variable holding the total score of the candidate
    private int scoreCount;
    // variable holding the number of TOTAL ATTEMPTED questions by the candidate
    private int totalQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method captures the users name before they can get access to the questions.
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

    /**
     * This is a method to restart the app.
     */
    public void restartApp(View view) {
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    /**
     * This method captures all input from the user for calculation and grading and displays the final score.
     */
    public void submitAnswers(View view) {
        // get all relevant data for RadioButton related questions
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

        // get all relevant data for EditText related questions
        EditText candidateAnswerTViewQ5 = findViewById(R.id.optionQ5_a);
        String rightAnswerQ5 = getResources().getString(R.string.q5_option_a);

        // get all relevant data for CheckBox related questions
        CheckBox rightAnswer1Q4 = findViewById(R.id.optionQ4_a);
        CheckBox rightAnswer2Q4 = findViewById(R.id.optionQ4_d);
        CheckBox wrongAnswer1Q4 = findViewById(R.id.optionQ4_b);
        CheckBox wrongAnswer2Q4 = findViewById(R.id.optionQ4_c);
        CheckBox rightAnswer1Q6 = findViewById(R.id.optionQ6_a);
        CheckBox rightAnswer2Q6 = findViewById(R.id.optionQ6_c);
        CheckBox wrongAnswer1Q6 = findViewById(R.id.optionQ6_b);
        CheckBox wrongAnswer2Q6 = findViewById(R.id.optionQ6_d);

        // send the data to the relevant grade methods for scoring
        gradeRadioQuestions(radioGroupQ1, rightAnswerQ1);
        gradeRadioQuestions(radioGroupQ2, rightAnswerQ2);
        gradeRadioQuestions(radioGroupQ3, rightAnswerQ3);
        gradeRadioQuestions(radioGroupQ7, rightAnswerQ7);
        gradeRadioQuestions(radioGroupQ8, rightAnswerQ8);

        gradeTextQuestions(candidateAnswerTViewQ5, rightAnswerQ5);

        gradeCheckQuestions(rightAnswer1Q4, rightAnswer2Q4, wrongAnswer1Q4, wrongAnswer2Q4);
        gradeCheckQuestions(rightAnswer1Q6, rightAnswer2Q6, wrongAnswer1Q6, wrongAnswer2Q6);

        // display the candidate score
        Toast.makeText(this, "Hello " + candidateName + ", you got " + scoreCount + " ANSWERS RIGHT out of " + totalQuestions + " ATTEMPTED questions", Toast.LENGTH_LONG).show();

        // reset the scoring data
        scoreCount = 0;
        totalQuestions = 0;
    }

    /**
     * This method calculates the score of all questions that require text answers.
     * <p>
     *
     * @param candidateAnswerTView The TextView holding the candidate answer.
     * @param rightAnswer          The correct answer to this question in String type.
     */
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
        }
    }

    /**
     * This method calculates the score of all questions that require checkboxed answers.
     * <p>
     *
     * @param rightAnswer1 The CheckBox holding the first correct answer.
     * @param rightAnswer2 The CheckBox holding the second correct answer.
     * @param wrongAnswer1 The CheckBox holding the first wrong answer.
     * @param wrongAnswer2 The CheckBox holding the second wrong answer.
     */
    public void gradeCheckQuestions(CheckBox rightAnswer1, CheckBox rightAnswer2, CheckBox wrongAnswer1, CheckBox wrongAnswer2) {
        if (wrongAnswer1.isChecked() || wrongAnswer2.isChecked()) {
            totalQuestions += 1;
            wrongAnswer1.setChecked(false);
            wrongAnswer2.setChecked(false);
            rightAnswer1.setChecked(true);
            rightAnswer2.setChecked(true);
            rightAnswer1.setTextColor(getResources().getColor(R.color.colorPrimaryText));
            rightAnswer2.setTextColor(getResources().getColor(R.color.colorPrimaryText));
        } else if (rightAnswer1.isChecked() && rightAnswer2.isChecked()) {
            scoreCount += 1;
            totalQuestions += 1;
            wrongAnswer1.setChecked(false);
            wrongAnswer2.setChecked(false);
            rightAnswer1.setChecked(true);
            rightAnswer2.setChecked(true);
            rightAnswer1.setTextColor(getResources().getColor(R.color.colorPrimaryText));
            rightAnswer2.setTextColor(getResources().getColor(R.color.colorPrimaryText));
        }
    }

    /**
     * This method calculates the score of all questions that require RadioButton answers.
     * <p>
     *
     * @param radioGroup  The RadioGroup holding all the RadioButtons for the question.
     * @param rightAnswer The int id of the RadioButton holding the correct answer.
     */
    public void gradeRadioQuestions(RadioGroup radioGroup, int rightAnswer) {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            return;
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
