package com.roche.hashcode.model;

import java.util.HashSet;

import static java.util.Objects.isNull;

public class Library {

    private HashSet<Book> books;
    private int daysToTakeSign;
    private int limitToShipBooksByDay;

    public Library(HashSet<Book> books, int daysToTakeSign, int limitToShipBooksByDay) {
        this.books = books;
        this.daysToTakeSign = daysToTakeSign;
        this.limitToShipBooksByDay = limitToShipBooksByDay;
    }

    public int totalScore() {
        if (isNull(books)) {
            return 0;
        } else {
            return books.stream().mapToInt(Book::getScore).sum();
        }
    }

    public HashSet<Book> getBooks() {
        return books;
    }

    public int getDaysToTakeSign() {
        return daysToTakeSign;
    }

    public int getLimitToShipBooksByDay() {
        return limitToShipBooksByDay;
    }

    @Override
    public String toString() {
        return "Library{" +
                "books=" + books +
                ", daysToTakeSign=" + daysToTakeSign +
                ", limitToShipBooksByDay=" + limitToShipBooksByDay +
                ", score=" + totalScore() +
                '}';
    }
}
