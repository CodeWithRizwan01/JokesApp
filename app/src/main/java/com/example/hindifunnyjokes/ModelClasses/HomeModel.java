package com.example.hindifunnyjokes.ModelClasses;

import com.example.hindifunnyjokes.R;

public class HomeModel {
    int imageView;
    String textView;

    public HomeModel(int imageView, String textView) {
        this.imageView = imageView;
        this.textView = textView;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getTextView() {
        return textView;
    }

    public void setTextView(String textView) {
        this.textView = textView;
    }
}
