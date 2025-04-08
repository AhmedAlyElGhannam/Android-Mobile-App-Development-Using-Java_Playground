package com.example.lab_02;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    TextView viewName;
    TextView viewPhone;
    Button buttonBacc;
    Button buttonWrPref;
    Button buttonRdPref;
    Button buttonWrIntMem;
    Button buttonRdIntMem;
    Button buttonWrSQL;
    Button buttonRdSQL;


    private static final String PREFERENCE = "PREFERENCE";

    private static final String MOBILE_NUM = "MOBILE_NUM";
    private static final String NAME = "NAME";
    private static final String PRIV_FILE = "PRIV_FILE";

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

        /* preference shenanigans */
        buttonWrPref = (Button) findViewById(R.id.wrShPref);
        buttonRdPref = (Button) findViewById(R.id.rdShPref);

        buttonWrPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(SecondActivity.NAME, viewName.getText().toString());
                editor.putString(SecondActivity.MOBILE_NUM, viewPhone.getText().toString());
                editor.commit();
                viewName.setText("");
                viewPhone.setText("");
            }
        });

        buttonRdPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
                viewName.setText(preferences.getString(SecondActivity.NAME, "N/A"));
                viewPhone.setText(preferences.getString(SecondActivity.MOBILE_NUM, "N/A"));
            }
        });

        /* internal mem file shenanigans */
        buttonWrIntMem = (Button) findViewById(R.id.wrIntMem);
        buttonRdIntMem = (Button) findViewById(R.id.readIntMem);

        buttonWrIntMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fos = openFileOutput(PRIV_FILE, Context.MODE_PRIVATE);
                    DataOutputStream dos = new DataOutputStream(fos);
                    dos.writeUTF(viewName.getText().toString());
                    dos.writeUTF(viewPhone.getText().toString());
                    dos.close();
                    fos.close();
                    viewName.setText("");
                    viewPhone.setText("");
                } catch (FileNotFoundException e) {
                    Toast.makeText(SecondActivity.this, "No file found", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(SecondActivity.this, "IO error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonRdIntMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fis = openFileInput(PRIV_FILE);
                    DataInputStream dis = new DataInputStream(fis);
                    viewName.setText(dis.readUTF());
                    viewPhone.setText(dis.readUTF());
                    dis.close();
                    fis.close();
                } catch (FileNotFoundException e) {
                    Toast.makeText(SecondActivity.this, "No file found", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(SecondActivity.this, "IO error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /* SQL shenanigans */
        buttonWrSQL = (Button) findViewById(R.id.wrSQL);
        buttonRdSQL = (Button) findViewById(R.id.rdSQL);

        buttonWrSQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageAdapter adapter = new MessageAdapter(SecondActivity.this);
                long id = adapter.insertMessage(new Message(viewName.getText().toString(), viewPhone.getText().toString()));
                viewName.setText("");
                viewPhone.setText("");
            }
        });

        buttonRdSQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageAdapter adapter = new MessageAdapter(SecondActivity.this);
                Message msg = adapter.findMessage(viewPhone.getText().toString());
                viewName.setText(msg.getName());
                viewPhone.setText(msg.getNumber());
            }
        });
    }
}