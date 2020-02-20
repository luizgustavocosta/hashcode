package com.roche.hashcode.library;

import com.roche.hashcode.model.Book;
import com.roche.hashcode.model.BookScanning;
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
        final List<String> lines = Files.readAllLines(Paths.get("src/test/resources/c_incunabula.txt"));
        final String[] order = lines.get(0).split(" ");
        //Fixed
        BookScanning bookScanning = new BookScanning();
        bookScanning.setTimeAvailable(Integer.parseInt(order[2]));

        int books = Integer.parseInt(order[0]);
        int libraries = Integer.parseInt(order[1]);

        List<Integer> scoreOfTheBooks = LibraryUtil.listOf(lines.get(1));

        for (int index = 3; index < lines.size(); index=index+2) {
            final String[] dataOfLibrary = lines.get(index).split(" ");
            HashSet<Book> booksOfFirstLibrary = new HashSet<>();
            for (String score : dataOfLibrary) {
                booksOfFirstLibrary.add(new Book(Integer.parseInt(score)));
            }
            final Library library = new Library(booksOfFirstLibrary,
                    Integer.parseInt(dataOfLibrary[1]),
                    Integer.parseInt(dataOfLibrary[2]));

            bookScanning.addLibrary(library);
        }
        System.out.println(bookScanning);
    }
}
