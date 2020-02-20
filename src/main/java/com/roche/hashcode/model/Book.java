package com.roche.hashcode.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Book {

    private final int score;
    private final int identifier;

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
