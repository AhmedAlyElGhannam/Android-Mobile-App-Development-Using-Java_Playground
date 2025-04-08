package com.example.lab_02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    TextView viewName;
    TextView viewPhone;
    Button buttonBacc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        viewPhone = findViewById(R.id.viewPhone);
        viewName = findViewById(R.id.viewName);
        buttonBacc = findViewById(R.id.buttonBacc);
        Intent incomingIntent = getIntent();
        String mobile = incomingIntent.getStringExtra("MOBILE_NUM");
        String name = incomingIntent.getStringExtra("NAME");
        viewName.setText(name);
        viewPhone.setText(mobile);
        buttonBacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}