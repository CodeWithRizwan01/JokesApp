package com.example.hindifunnyjokes.ModelClasses;

public class JokesModel {
    private int originalImage;
    private int changedImage1;
    private int changedImage2;
    String jokeTitle;
    String textView;
    private int currentImage;

    public JokesModel(int originalImage, int changedImage1, int changedImage2, String jokeTitle, String textView) {
        this.originalImage = originalImage;
        this.changedImage1 = changedImage1;
        this.changedImage2 = changedImage2;
        this.jokeTitle = jokeTitle;
        this.textView = textView;
        this.currentImage = originalImage; // Initialize with the original image
    }

    public void toggleImage() {
        if (currentImage == originalImage) {
            currentImage = changedImage1;
        } else if (currentImage == changedImage1) {
            currentImage = changedImage2;
        } else {
            currentImage = originalImage;
        }
    }

    public int getImageView() {
        return currentImage;
    }

    public String getJokeTitle() {
        return jokeTitle;
    }

    public void setJokeTitle(String jokeTitle) {
        this.jokeTitle = jokeTitle;
    }

    public String getTextView() {
        return textView;
    }

    public void setTextView(String textView) {
        this.textView = textView;
    }
}
