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
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    Button btnDownloadImg;
    TextView txtURL;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnDownloadImg = findViewById(R.id.btnDownloadImg);
        txtURL = findViewById(R.id.txtURL);
        imgView = findViewById(R.id.imgView);
        Handler handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                imgView.setImageBitmap((Bitmap) msg.obj);
            }
        };
        // onclick listener for button launches download thread
        btnDownloadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Bitmap img = download(txtURL.getText().toString());
                            Message msg = new Message();
                            msg.obj = img;
                            handler.sendMessage(msg);
                        } catch (IOException e) {
                            // handle exception
                        }
                    }
                }.start();
            }
        });
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
            Bitmap result =  null;
            try {
                result = download(strings[0]);
            } catch (RuntimeException e) {

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return result;
        }

//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
    }
}