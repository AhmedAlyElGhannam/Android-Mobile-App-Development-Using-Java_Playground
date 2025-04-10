package com.example.lab06_task01;

import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

public class Meme {
    String txtCaption;
    int imgTemplate;

    public Meme(String txtCaption, int imgTemplate) {
        this.txtCaption = txtCaption;
        this.imgTemplate = imgTemplate;
    }

    public String getTxtCaption() {
        return txtCaption;
    }

    public void setTxtCaption(String txtCaption) {
        this.txtCaption = txtCaption;
    }

    public int getImgTemplate() {
        return imgTemplate;
    }

    public void setImgTemplate(int imgTemplate) {
        this.imgTemplate = imgTemplate;
    }
}
