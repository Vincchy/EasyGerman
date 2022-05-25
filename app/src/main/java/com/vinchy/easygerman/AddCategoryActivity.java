package com.vinchy.easygerman;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCategoryActivity extends AppCompatActivity {

    EditText newCategoryName;
    Button addCategoryButton;
    DatabaseHelper dbHelper;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        setTitle("Add category");

        // --- Private variables setup
        newCategoryName = findViewById(R.id.txtNewCategoryName);
        addCategoryButton = findViewById(R.id.btnAddCategory);
        dbHelper = new DatabaseHelper(this);


        // ---

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String categoryName = newCategoryName.getText().toString();
                if (categoryName.length() == 0)
                    Toast.makeText(AddCategoryActivity.this, "Category name cannot be empty", Toast.LENGTH_SHORT).show();
                else if (categoryName.length() > 50)
                    Toast.makeText(AddCategoryActivity.this, "Category name too long", Toast.LENGTH_SHORT).show();
                else
                    dbHelper.addCategory(categoryName);

                Intent intent = new Intent(AddCategoryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
