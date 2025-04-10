package com.example.lab06_task01;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RMemeAdapter extends RecyclerView.Adapter<RMemeAdapter.ViewHolder>{

    private final Context context;
    private List<Meme> memes;
    private static final String TAG = "RADAPTER";

    public RMemeAdapter(Context context, List<Meme> memes) {
        this.context = context;
        this.memes = memes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View layout;
        private TextView txtCaption;
        private ImageView imgCaption;
        public ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            txtCaption = layout.findViewById(R.id.txtCaption);
            imgCaption = layout.findViewById(R.id.imgTemplate);
            constraintLayout = layout.findViewById(R.id.memeLayout);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.meme_row, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);
        Log.i(TAG, "onCreateViewHolder: ");
        return vh;
    }

    @NonNull
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtCaption.setText(memes.get(position).getTxtCaption());
        holder.imgCaption.setImageResource(memes.get(position).getImgTemplate());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, memes.get(holder.getAdapterPosition()).getTxtCaption(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.i(TAG, "onBindViewHolder: ");
    }

    @NonNull
    @Override
    public int getItemCount() {
        return memes.size();
    }
}
