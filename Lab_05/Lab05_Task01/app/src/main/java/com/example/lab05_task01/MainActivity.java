package com.example.lab05_task01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

//    Button btnNext;
//    Button btnPrev;
    TextView txtViewTitle;
    TextView txtViewDesc;
    TextView txtViewPrice;
    RatingBar ratingBar;
    ImageView imgView;
    Product[] productsInfo;
    Handler jsonHandler;
    Handler imgHandler;

    int productCounter = 0;

    private static final int NUM_OF_PRODUCTS = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // initializing array of products
        productsInfo = new Product[NUM_OF_PRODUCTS];
//
//        btnNext = findViewById(R.id.btnNext);
//        btnPrev = findViewById(R.id.btnPrev);

        txtViewTitle = findViewById(R.id.txtViewTitle);
        txtViewPrice = findViewById(R.id.txtViewPrice);
        txtViewDesc = findViewById(R.id.txtViewDesc);

        ratingBar = findViewById(R.id.ratingBar);

        imgView = findViewById(R.id.imgView);

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
                            productsInfo[i] = new Product();
                            productsInfo[i].setTitle(c.getString("title"));
                            productsInfo[i].setDescription(c.getString("description"));
                            productsInfo[i].setPrice(c.getString("price"));
                            productsInfo[i].setRating((float) c.getDouble("rating"));
                            productsInfo[i].setThumbnail(c.getString("thumbnail"));
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
                updateUI();
            }
        };

        imgHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                imgView.setImageBitmap((Bitmap) msg.obj);
            }
        };

//        // onclick listener for next product button
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (productCounter < (NUM_OF_PRODUCTS - 1)) {
//                    productCounter++;
//                    updateUI();
//                }
//            }
//        });
//        // onclick listener for prev product button
//        btnPrev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (productCounter > 0) {
//                    productCounter--;
//                    updateUI();
//                }
//            }
//        });
    }

    void updateUI() {
        txtViewTitle.setText(productsInfo[productCounter].getTitle());
        txtViewPrice.setText(productsInfo[productCounter].getPrice());
        txtViewDesc.setText(productsInfo[productCounter].getDescription());
        ratingBar.setRating(productsInfo[productCounter].getRating());
        new DownloadTask().execute(productsInfo[productCounter].getThumbnail());
    }

    private Bitmap download(String url) throws IOException, MalformedURLException {
        URL imageURL = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) imageURL.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        // switch on response code
        if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK)
        {
            InputStream is = connection.getInputStream(); // same thing as connecting
            // read until EOF is reached
            return BitmapFactory.decodeStream(is);
        }
        else {
            return null;
        }
    }

    class DownloadTask extends AsyncTask<String,Integer,Bitmap> {
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgView.setImageBitmap(bitmap);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap img =  null;
            try {
                img = download(strings[0]);
                Message msg = new Message();
                msg.obj = img;
                imgHandler.sendMessage(msg);
            } catch (IOException e) {
                // handle exception
            }
            return img;
        }
    }
}