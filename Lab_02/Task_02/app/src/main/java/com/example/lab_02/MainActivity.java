package com.example.lab_02;

//import static com.example.lab_02.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button buttonNext;
    private Button buttonExit;
    private EditText editTextPhone;
    private EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        buttonNext = findViewById(R.id.buttonNext);

        buttonExit = findViewById(R.id.buttonExit);

        editTextPhone = findViewById(R.id.editTextPhone);

        editTextName = findViewById(R.id.editTextName);
    }

    public void nextAct(View view) {
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("MOBILE_NUM", editTextPhone.getText().toString());
        intent.putExtra("NAME", editTextName.getText().toString());
        startActivity(intent);
    }

    public void exitAct(View view) {
        finish();
    }
}