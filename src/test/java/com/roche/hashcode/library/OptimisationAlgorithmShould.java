package com.roche.hashcode.library;

import com.roche.hashcode.model.Book;
import com.roche.hashcode.model.Library;
import com.roche.hashcode.model.ResponseOutput;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OptimisationAlgorithmShould {

    @Test
    public void returnOutputFileForExampleOne() {
//        Integer books = 6;
        Book bookZero = new Book(1, 0);
        Book bookOne = new Book(2, 1);
        Book bookTwo = new Book(3, 2);
        Book bookThree = new Book(6, 3);
        Book bookFour = new Book(5, 4);
        Book bookFive = new Book(4, 5);

        Library libraryOne = new Library(new HashSet<Book>(Arrays.asList(bookZero, bookOne, bookTwo, bookThree)), 2, 2);
        List<Library> libraries = new ArrayList<>();
        Integer days = 7;


        ResponseOutput responseOutput = ResponseOutput.builder()
                .library(ResponseOutput.LibraryOutput.builder()
                        .books(Arrays.asList(bookZero, bookOne, bookTwo, bookThree, bookFour))
                        .identifier(0)
                        .build())
                .library(ResponseOutput.LibraryOutput.builder()
                        .identifier(1)
                        .books(Arrays.asList(bookFive))
                        .build())
                .build();

        assertThat(responseOutput.libraryAmount()).isEqualTo(2);
        assertThat(responseOutput.getLibraries().get(0).getBooks().size()).isEqualTo(5);
        assertThat(responseOutput.getLibraries().get(0).getScannedBooks()).contains(
                bookZero.getIdentifier(),
                bookOne.getIdentifier(),
                bookTwo.getIdentifier(),
                bookThree.getIdentifier(),
                bookFour.getIdentifier());

        assertThat(responseOutput.getLibraries().get(1).getBooks().size()).isEqualTo(1);
        assertThat(responseOutput.getLibraries().get(1).getScannedBooks()).contains(bookFive.getIdentifier());
    }
}
