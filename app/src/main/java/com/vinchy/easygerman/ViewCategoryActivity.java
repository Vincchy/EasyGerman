package com.vinchy.easygerman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewCategoryActivity extends AppCompatActivity {

    private Button addWordsButton;
    private Button shuffleWordsButton, shuffleTranslationsButton;
    private Button viewWordlistButton;

    // Name of the current category
    private String categoryName;

    // On back pressed go to the MainActivity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewCategoryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        setTitle(getSharedPreferences("shared_prefs", MODE_PRIVATE).getString("lastCategoryName", null));

        addWordsButton = findViewById(R.id.btnAddWords);
        shuffleWordsButton = findViewById(R.id.btnShuffleWords);
        shuffleTranslationsButton = findViewById(R.id.btnShuffleTranslations);
        viewWordlistButton = findViewById(R.id.btnViewWordlist);

        // Set the listener for adding new words
        addWordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewCategoryActivity.this, AddWordActivity.class);
                startActivity(intent);
            }
        });

        viewWordlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewCategoryActivity.this, WordlistActivity.class);
                startActivity(intent);
            }
        });

        shuffleWordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewCategoryActivity.this, QuizShuffleWordsActivity.class);
                startActivity(intent);
            }
        });

    }
}
