package com.roche.hashcode.library;

import com.roche.hashcode.model.Book;
import com.roche.hashcode.model.Library;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

public class ScanningBooksShould {

    @Test
    public void readAFile() throws IOException {
        Files.readAllLines(Paths.get("src/test/resources/a_example.txt")).forEach(System.out::println);
    }

    @Test
    public void convertInputToModel() throws IOException {
        final List<String> lines = Files.readAllLines(Paths.get("src/test/resources/a_example.txt"));
        final String[] order = lines.get(0).split(" ");
        //Fixed
        int books = Integer.parseInt(order[0]);
        int libraries = Integer.parseInt(order[1]);
        int daysForScanning = Integer.parseInt(order[2]);

        List<Integer> scores = LibraryUtil.listOf(lines.get(1));

        int jumpLines = 2;

        for (int index = 2; index < lines.size(); index=index*2) {
            final String[] dataOfLibrary = lines.get(index).split(" ");

            HashSet<Book> booksOfFirstLibrary = new HashSet<>();
            for (String score : lines.get(index + 1).split(" ")) {
                booksOfFirstLibrary.add(new Book(Integer.parseInt(score)));
            }
            final Library library = new Library(booksOfFirstLibrary,
                    Integer.parseInt(dataOfLibrary[1]),
                    Integer.parseInt(dataOfLibrary[2]));
            System.out.println(library);
        }

        /*HashSet<Book> booksOfFirstLibrary = new HashSet<>();
        for (String score : lines.get(3).split(" ")) {
            booksOfFirstLibrary.add(new Book(Integer.parseInt(score)));
        }
        final String[] dataOfLibrary = lines.get(2).split(" ");
        final Library library = new Library(booksOfFirstLibrary,
                Integer.parseInt(dataOfLibrary[1]),
                Integer.parseInt(dataOfLibrary[2]));
        System.out.println(library);*/
        //System.out.println(books+"#"+libraries+"#"+day
        // sForScanning);

    }

}
