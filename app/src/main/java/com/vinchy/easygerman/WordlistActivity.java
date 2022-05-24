package com.vinchy.easygerman;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WordlistActivity extends AppCompatActivity {
    private RecyclerView rv_wordlist;
    private DatabaseHelper dbHelper;
    private TextView tv_noWordsMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlist);

        rv_wordlist = findViewById(R.id.rv_wordlist);
        dbHelper = new DatabaseHelper(this);
        tv_noWordsMessage = findViewById(R.id.tv_noWordsMessage);

        // Get current category name from shared preferences
        String categoryName = getSharedPreferences("shared_prefs", MODE_PRIVATE).getString("lastCategoryName", null);
        // Query for wordlist and set it for RecyclerView
        ArrayList<Bundle> words = dbHelper.getWordsFromCategory(categoryName);

        // Display "No words yet" message when the array is empty
        if (words.size() == 0) {
            tv_noWordsMessage.setVisibility(View.VISIBLE);
        } else { // Else display the words in order
            WordlistRVAdapter adapter = new WordlistRVAdapter(this);
            adapter.setWords(words);

            rv_wordlist.setAdapter(adapter);
            rv_wordlist.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
