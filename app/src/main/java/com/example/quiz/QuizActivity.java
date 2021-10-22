package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {


    TextView questionTextView;
    Button btn;
    //private RequestQueue queue;
    final String url_prefix = "https://opentdb.com/api.php?amount=10&";
    final String url_suffix = "&type=multiple";
    List<List<String>> optionsList = new ArrayList<List<String>>();

    List<String> questionsList = new ArrayList<String>();
    List<Integer> correctAnswerIndexList = new ArrayList<Integer>();
    String question,field,difficulty;

    int currentQuestionIndex=0;
    int currentCorrectAnswerIndex;
    boolean answered=false;
    int score=0;

    private void getQuestions()
    {
        //API call
        final RequestQueue queue = Volley.newRequestQueue(this);
        String url = url_prefix + "category=" + field + "&difficulty=" + difficulty.toLowerCase() + url_suffix;
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        for (int i = 0; i < 10; i++) {
                            render_questions(response, i);
                        }
                        displayQuestions(0);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error Response", error.toString());
                    }
                }
        );
        queue.add(objectRequest);
    }

    private void displayQuestions(int index) {
        questionTextView = findViewById(R.id.questionText); questionTextView.setText(questionsList.get(index));
        btn = findViewById(R.id.option1); btn.setText(optionsList.get(index).get(0).toString()); btn.setBackgroundColor(Color.rgb(187,134,252));
        btn = findViewById(R.id.option2); btn.setText(optionsList.get(index).get(1).toString()); btn.setBackgroundColor(Color.rgb(187,134,252));
        btn = findViewById(R.id.option3); btn.setText(optionsList.get(index).get(2).toString()); btn.setBackgroundColor(Color.rgb(187,134,252));
        btn = findViewById(R.id.option4); btn.setText(optionsList.get(index).get(3).toString()); btn.setBackgroundColor(Color.rgb(187,134,252));
        currentCorrectAnswerIndex = correctAnswerIndexList.get(index);
    }

    private void render_questions(JSONObject response, int k) {
        try {
            JSONArray arr = response.getJSONArray("results");
            JSONObject j = arr.getJSONObject(k);

            question = j.get("question").toString();
            questionsList.add(question);

            JSONArray options = j.getJSONArray("incorrect_answers");

            String correct_option = j.get("correct_answer").toString();

            //to shuffle options using an arraylist
            List<String> currentOptions = new ArrayList<String>();
            if (options != null) {
                for (int i=0;i<options.length();i++){
                    currentOptions.add(options.getString(i));
                }
            }
            currentOptions.add(correct_option);
            Collections.shuffle(currentOptions);
            correctAnswerIndexList.add(currentOptions.indexOf(correct_option));
            optionsList.add(currentOptions);

        } catch (JSONException e) {
            Log.e("ERROR Response",e.toString());
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Intent intent = getIntent();
        difficulty = intent.getStringExtra(MainActivity.CHOSEN_DIFFICULTY);
        field = intent.getStringExtra(MainActivity.CHOSEN_FIELD);
        getQuestions();

        checkAnswers();
    }

    private void checkAnswers() {
        Button optionButton1 = (Button) findViewById(R.id.option1);
        Button optionButton2 = (Button) findViewById(R.id.option2);
        Button optionButton3 = (Button) findViewById(R.id.option3);
        Button optionButton4 = (Button) findViewById(R.id.option4);
        answered = false;
        optionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answered==false);
                {
                    answered = true;
                    checkIfCorrect(optionButton1.getText());
                }
            }
        });

        optionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answered==false);
                {
                    answered = true;
                    checkIfCorrect(optionButton2.getText());
                }
            }
        });

        optionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answered==false);
                {
                    answered = true;
                    checkIfCorrect(optionButton3.getText());
                }
            }
        });

        optionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answered==false);
                {
                    answered = true;
                    checkIfCorrect(optionButton4.getText());
                }
            }
        });
    }

    private void checkIfCorrect(CharSequence text) {
        if(text.toString() == optionsList.get(currentQuestionIndex).get(correctAnswerIndexList.get(currentQuestionIndex)))
        {
            score++;
        }
        if(correctAnswerIndexList.get(currentQuestionIndex)==0)
        {
            Button optionButton = (Button) findViewById(R.id.option1);
            optionButton.setBackgroundColor(Color.GREEN);
        }
        else if(correctAnswerIndexList.get(currentQuestionIndex)==1)
        {
            Button optionButton = (Button) findViewById(R.id.option2);
            optionButton.setBackgroundColor(Color.GREEN);
        }
        else if(correctAnswerIndexList.get(currentQuestionIndex)==2)
        {
            Button optionButton = (Button) findViewById(R.id.option3);
            optionButton.setBackgroundColor(Color.GREEN);
        }
        else if(correctAnswerIndexList.get(currentQuestionIndex)==3)
        {
            Button optionButton = (Button) findViewById(R.id.option4);
            optionButton.setBackgroundColor(Color.GREEN);
        }
        //switching to next question or score display
        if(currentQuestionIndex<9)
        {
            currentQuestionIndex++;
        }
        else
        {
            //score display
            new AlertDialog.Builder(QuizActivity.this)
                    .setTitle("Score")
                    .setMessage("You Scored " + Integer.toString(score) + "/10")
                    .setCancelable(false)
                    .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }).show();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                displayQuestions(currentQuestionIndex);
            }
        },2000);
    }


}