package com.vinchy.easygerman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTranslationActivity extends AppCompatActivity {

    private Button addWordButton;
    private DatabaseHelper dbHelper;
    private EditText editTxtTranslation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_translation);

        setTitle("Add word");

        dbHelper = new DatabaseHelper(AddTranslationActivity.this);

        editTxtTranslation = findViewById(R.id.editTxtNewTranslation);
        addWordButton = findViewById(R.id.btnFinishAddingWord);
        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTranslationActivity.this, ViewCategoryActivity.class);

                // Get category name from shared preferences (set in onClickListener in CategoriesRVAdapter)
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
                String categoryName = sharedPreferences.getString("lastCategoryName", "");
                if (categoryName.length() == 0) {
                    Toast.makeText(AddTranslationActivity.this, "Category name empty", Toast.LENGTH_SHORT).show();

                    // Skip adding the word and go straight to the ViewCategoryActivity
                    startActivity(intent);
                    finish();
                }

                Intent valueIntent = getIntent();
                dbHelper.addWord(
                        valueIntent.getStringExtra("word"), // Word
                        valueIntent.getStringExtra("article"), // Article
                        editTxtTranslation.getText().toString(), // Translation
                        "noun", // Word type
                        dbHelper.getCategoryID(categoryName)); // Category ID

                /*
                Log.e("Word", valueIntent.getStringExtra("word"));
                Log.e("Article", valueIntent.getStringExtra("article"));
                Log.e("Translation", editTxtTranslation.getText().toString());
                Log.e("Category", valueIntent.getStringExtra("categoryName"));
                 */

                startActivity(intent);
                finish();
            }
        });
    }
}
