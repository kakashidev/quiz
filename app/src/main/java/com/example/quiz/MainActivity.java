package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner fieldSpinner, difficultySpinner;
    String[] field = {"Select", "General Knowledge", "Computers", "Sports", "Comics", "Video Games"};
    int[] fieldNum = {0,9,18,21,29,15};
    String[] difficulty = {"Select", "Easy", "Medium", "Hard"};
    MaterialButton start_button;

    public static final String CHOSEN_FIELD = "com.example.quiz.CHOSEN_FIELD";
    public static final String CHOSEN_DIFFICULTY = "com.example.quiz.CHOSEN_DIFFICULTY";
    int chosenField = 0;
    int chosenDifficulty = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldSpinner=findViewById(R.id.fieldSpinner);
        difficultySpinner=findViewById(R.id.difficultySpinner);

        ArrayAdapter<String> fieldAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, field);
        fieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fieldSpinner.setAdapter(fieldAdapter);

        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, difficulty);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyAdapter);


        fieldSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosenField=fieldNum[i];
                Log.d("response_chosen", Integer.toString(chosenField));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Select a Field", Toast.LENGTH_SHORT).show();
            }
        });

        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosenDifficulty=i;
                Log.d("response_chosen", Integer.toString(chosenDifficulty));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Select a Difficulty", Toast.LENGTH_SHORT).show();
            }
        });


        start_button=(MaterialButton) findViewById(R.id.start_btn);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chosenField!= 0 && chosenDifficulty!= 0)
                {
                    goToQuizActivity();
                }
            }
        });

    }

    public void goToQuizActivity(){
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra(CHOSEN_FIELD,Integer.toString(chosenField));
        intent.putExtra(CHOSEN_DIFFICULTY,difficulty[chosenDifficulty]);
        startActivity(intent);
    }

    public String getChosenField()
    {
        Log.d("response field", Integer.toString(chosenField));
        return Integer.toString(chosenField);
    }
    public String getChosenDifficulty()
    {
        Log.d("response diff", difficulty[chosenDifficulty]);
        return difficulty[chosenDifficulty];
    }



}