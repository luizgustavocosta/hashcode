package com.roche.hashcode.library;

import com.roche.hashcode.model.Book;
import com.roche.hashcode.model.BookScanning;
import com.roche.hashcode.model.Library;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.stream.Collectors;

public class ScanningBooksShould {

    @Test
    public void readAFile() throws IOException {
        Files.readAllLines(Paths.get("src/test/resources/a_example.txt")).forEach(System.out::println);
    }

    public BookScanning readInput(String input) throws IOException {
        final List<String> lines = Files.readAllLines(Paths.get(input));
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
        return bookScanning;
    }

    @Test
    public void orderTheScanningToScenarioC() throws IOException {
        final BookScanning bookScanning = readInput("src/test/resources/g_example.txt");
        final List<Library> libraries = bookScanning.getLibraries()
                .stream()
                .sorted(byScore.thenComparing(byDays).reversed())
                .sorted(byLimit)
                .collect(Collectors.toList());

        System.out.println(libraries);
    }

    Comparator<Library> byScore = Comparator.comparing(Library::totalScore);
    Comparator<Library> byDays = Comparator.comparing(Library::getDaysToTakeSign);
    Comparator<Library> byLimit = Comparator.comparing(Library::getLimitToShipBooksByDay);
}
