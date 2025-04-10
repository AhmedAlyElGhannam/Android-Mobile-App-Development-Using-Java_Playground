package com.example.lab06_task02;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Handler jsonHandler;

    int productCounter = 0;

    RProductAdapter rProductAdapter;

    List<Product> productsInfo;

    RecyclerView recyclerView;

    private static final int NUM_OF_PRODUCTS = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        productsInfo = new ArrayList<>();

        // this thread is responsible for parsing the json and storing its data
        new Thread() {
            @Override
            public void run() {
                HttpHandler sh = new HttpHandler();
                // Making a request to url and getting response
                String url = "https://dummyjson.com/products";
                String jsonStr = sh.makeServiceCall(url);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // Getting JSON Array node
                        JSONArray products = jsonObj.getJSONArray("products");

                        // looping through All Contacts
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);
                            Product p = new Product();
                            p.setTitle(c.getString("title"));
                            p.setDescription(c.getString("description"));
                            p.setPrice(c.getString("price"));
                            p.setRating((float) c.getDouble("rating"));
                            p.setThumbnail(c.getString("thumbnail"));
                            productsInfo.add(p);
                        }

                        // send empty message to notify handler
                        Message msg = new Message();
                        jsonHandler.sendMessage(msg);
                    } catch (final JSONException e) {
                        // some exception handling
                    }
                }
            }
        }.start();

        jsonHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                rProductAdapter = new RProductAdapter(MainActivity.this, productsInfo);
                recyclerView.setAdapter(rProductAdapter);
            }
        };
    }
}