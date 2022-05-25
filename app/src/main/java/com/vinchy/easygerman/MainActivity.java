package com.vinchy.easygerman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // List of all categories
    private RecyclerView categoriesRV;

    private DatabaseHelper dbHelper;
    private FloatingActionButton addCategoryButton;
    private FloatingActionButton clearDatabaseButton;
    private TextView noCategoriesMessage;

    // Last time the back button has been pressed (for double tap exit)
    private long backPressedTime;
    private long doubleTapExitDelay;

    @Override
    public void onBackPressed() {
        if (backPressedTime + doubleTapExitDelay > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(MainActivity.this, "Press back again to leave", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // --- Private variables setup
        backPressedTime = 0;
        doubleTapExitDelay = 3000;

        dbHelper = new DatabaseHelper(this);

        categoriesRV = findViewById(R.id.rvCategories);
        addCategoryButton = findViewById(R.id.btnCreateCategoryActivity);
        clearDatabaseButton = findViewById(R.id.btnDeleteDatabase);
        noCategoriesMessage = findViewById(R.id.txtViewNoCategories);
        // ---

        // --- Recycler view setup
        ArrayList<String> categories = new ArrayList<>();
        categories.addAll(dbHelper.getCategories());
        if (categories.isEmpty())
            noCategoriesMessage.setVisibility(View.VISIBLE);
        else
            noCategoriesMessage.setVisibility(View.GONE);

        CategoriesRVAdapter categoriesAdapter = new CategoriesRVAdapter(this);
        categoriesAdapter.setCategoryNames(categories);

        categoriesRV.setAdapter(categoriesAdapter);
        categoriesRV.setLayoutManager(new LinearLayoutManager(this));
        // ---

        // New category button on click listener
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCategoryActivity.class);
                startActivity(intent);
            }
        });

        // Just for testing & debugging
        // TODO: Delete this button
        clearDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the database (testing)
                MainActivity.this.deleteDatabase("Words.db");
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}