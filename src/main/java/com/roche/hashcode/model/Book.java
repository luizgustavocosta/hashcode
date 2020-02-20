package com.roche.hashcode.model;

public class Book {

    private int score;

    public Book(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Book{" +
                "score=" + score +
                '}';
    }
}
