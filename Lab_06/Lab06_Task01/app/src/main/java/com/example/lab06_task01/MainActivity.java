package com.example.lab06_task01;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RMemeAdapter rMemeAdapter;

    List<Meme> memes;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        memes = Arrays.asList(
                new Meme("Perhaps", R.drawable.one),
                new Meme("Goals", R.drawable.two),
                new Meme("Uncanny", R.drawable.three),
                new Meme("Listen", R.drawable.four),
                new Meme("Our", R.drawable.five),
                new Meme("Goodbye", R.drawable.six),
                new Meme("Aight", R.drawable.seven),
                new Meme("Awadin Basha", R.drawable.eight)
        );

        rMemeAdapter = new RMemeAdapter(this, memes);

        recyclerView.setAdapter(rMemeAdapter);
    }
}