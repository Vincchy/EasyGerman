package com.vinchy.easygerman;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddWordActivity extends AppCompatActivity {

    private EditText newWord;
    private Spinner articleSpinner;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        setTitle("Add word");

        newWord = findViewById(R.id.editTxtNewWord);
        articleSpinner = findViewById(R.id.spinnerArticle);
        nextButton = findViewById(R.id.buttonNext);

        // Spinner setup (der, die, das articles)
        ArrayList<String> articles = new ArrayList<>();
        articles.add("der");
        articles.add("die");
        articles.add("das");

        ArrayAdapter<String> articleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, articles);
        articleSpinner.setAdapter(articleAdapter);

        // Button setup (switch to the next activity - add translation)
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddWordActivity.this, AddTranslationActivity.class);

                // Send the values to the AddTranslationActivity (later add them to a database)
                intent.putExtra("article", articleSpinner.getSelectedItem().toString());
                intent.putExtra("word", newWord.getText().toString());

                startActivity(intent);
            }
        });
    }
}
