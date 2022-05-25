package com.vinchy.easygerman;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class QuizShuffleWordsActivity extends AppCompatActivity {

    ViewFlipper viewFlipper;
    EditText etTranslation;
    TextView tvArticle, tvWord, tvFeedback;
    Button btnCheck;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_shuffle_words);

        viewFlipper = findViewById(R.id.vf_word_type);
        etTranslation = findViewById(R.id.et_translation);
        tvArticle = findViewById(R.id.tv_article);
        tvWord = findViewById(R.id.tv_word);
        btnCheck = findViewById(R.id.btn_check);
        tvFeedback = findViewById(R.id.tv_feedback);

        dbHelper = new DatabaseHelper(this);
        String categoryName = getSharedPreferences("shared_prefs", MODE_PRIVATE).getString("lastCategoryName", null);
        ArrayList<Bundle> words = dbHelper.getWordsFromCategory(categoryName);

        Random r = new Random();
        int index = r.nextInt(words.size());
        tvArticle.setText(words.get(index).getString("article"));
        tvWord.setText(words.get(index).getString("word"));

        viewFlipper.setDisplayedChild(0);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etTranslation.getText().toString().equals(words.get(index).getString("translation")))
                    tvFeedback.setVisibility(View.VISIBLE);
                else {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
}
