package com.example.hindifunnyjokes.ModelClasses;

public class Jokes {
    private int id;
    private String title;
    private String joke;

    public static final String JOKES_TABLE = "Jokes";
    public static final String KEY_ID = "Id";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_JOKE = "Joke";

    public static final String CREATE_JOKES_TABLE = String.format("CREATE TABLE IF NOT EXISTS %S (%S INTEGER PRIMARY KEY AUTOINCREMENT, %S TEXT, %S TEXT)", JOKES_TABLE, KEY_ID, KEY_TITLE, KEY_JOKE);
    public static final String DELETE_JOKES_TABLE = "DROP TABLE IF EXISTS " + JOKES_TABLE;
    public static final String SELECT_JOKES_TABLE = "SELECT * FROM " + JOKES_TABLE;

    public Jokes() {
    }

    public Jokes(int id, String title, String joke) {
        this.id = id;
        this.title = title;
        this.joke = joke;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

}
