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
        Book bookOne = new Book(1, 1);
        Book bookTwo = new Book(2, 2);
        Book bookThree = new Book(3, 3);
        Book bookFour = new Book(4, 6);
        Book bookFive = new Book(5, 5);
        Book bookSix = new Book(6, 4);

        Library libraryOne = new Library(new HashSet<Book>(Arrays.asList(bookOne, bookTwo, bookThree, bookFour)), 2, 2);
        List<Library> libraries = new ArrayList<>();
        Integer days = 7;


        ResponseOutput responseOutput = ResponseOutput.builder()
                .library(ResponseOutput.LibraryOutput.builder()
                        .identifier(0)
                        .build())
                .build();

        assertThat(responseOutput.libraryAmount()).isEqualTo(2);
        assertThat(responseOutput.getLibraries().get(0).getBooks().size()).isEqualTo(5);
        assertThat(responseOutput.getLibraries().get(0).getScannedBooks()).contains(0, 1, 2, 3, 4);

        assertThat(responseOutput.getLibraries().get(1).getBooks().size()).isEqualTo(1);
        assertThat(responseOutput.getLibraries().get(1).getScannedBooks()).contains(5);
    }
}
