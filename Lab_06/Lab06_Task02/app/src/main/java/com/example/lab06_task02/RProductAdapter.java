package com.example.lab06_task02;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class RProductAdapter extends RecyclerView.Adapter<RProductAdapter.ViewHolder>{

    private final Context context;
    private List<Product> products;
    private static final String TAG = "RADAPTER";

    public RProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View layout;
        private TextView txtViewTitle;
        private TextView txtViewDesc;
        private TextView txtViewPrice;
        private RatingBar ratingBar;
        private ImageView imgView;
        public ConstraintLayout productLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            txtViewTitle = layout.findViewById(R.id.txtViewTitle);
            txtViewDesc = layout.findViewById(R.id.txtViewDesc);
            txtViewPrice = layout.findViewById(R.id.txtViewPrice);
            ratingBar = layout.findViewById(R.id.ratingBar);
            imgView = layout.findViewById(R.id.imgView);
            productLayout = layout.findViewById(R.id.productLayout);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());
        View v = inflater.inflate(R.layout.productrow, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);
        Log.i(TAG, "onCreateViewHolder: ");
        return vh;
    }

    @NonNull
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtViewTitle.setText(products.get(position).getTitle());
        holder.txtViewDesc.setText(products.get(position).getDescription());
        holder.txtViewPrice.setText(products.get(position).getPrice());

        holder.ratingBar.setRating(products.get(position).getRating());

        new DownloadTask(holder.imgView).execute(products.get(position).getThumbnail());

        holder.productLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, products.get(holder.getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.i(TAG, "onBindViewHolder: ");
    }

    @NonNull
    @Override
    public int getItemCount() {
        return products.size();
    }

    private static Bitmap download(String url) throws IOException, MalformedURLException {
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

    static class DownloadTask extends AsyncTask<String,Integer,Bitmap> {
        private final ImageView imageView;

        public DownloadTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap img =  null;
            try {
                return download(strings[0]);
            } catch (IOException e) {
                // handle exception
            }
            return null;
        }
    }
}
