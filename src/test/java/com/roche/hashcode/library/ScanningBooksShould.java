package com.roche.hashcode.library;

import com.roche.hashcode.model.Book;
import com.roche.hashcode.model.BookScanning;
import com.roche.hashcode.model.Library;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        bookScanning.setNumberOfBooks(Integer.parseInt(order[0]));

        int libraries = Integer.parseInt(order[1]);

        List<Integer> scoreOfTheBooks = LibraryUtil.listOf(lines.get(1));

        for (int index = 3; index < lines.size(); index=index+2) {
            final String[] dataOfLibrary = lines.get(index).split(" ");
            HashSet<Book> booksOfFirstLibrary = new HashSet<>();
            for (String score : dataOfLibrary) {
                booksOfFirstLibrary.add(new Book(Integer.parseInt(score)));
            }
            final List<Book> sorted = booksOfFirstLibrary.stream()
                    .sorted(Comparator.comparingInt(Book::getScore)).collect(Collectors.toList());
            if (dataOfLibrary.length > 2) {
                final Library library = new Library(sorted,
                        Integer.parseInt(dataOfLibrary[1]),
                        Integer.parseInt(dataOfLibrary[2]));
                bookScanning.addLibrary(library);
            }
        }
        return bookScanning;
    }

    @Test
    public void generateOutputToScenario() throws IOException {
        String directory = "src/test/resources/";
        String fileName = "a_example.txt";
        final BookScanning bookScanning = readInput(directory+fileName);
        final List<Library> libraries = bookScanning.getLibraries()
                .stream()
                .sorted(bySignDuration)
                .sorted(byScore.thenComparing(bySignDuration).reversed())
                .sorted(byLimit)
                .collect(Collectors.toList());

        int numberOfBooks = bookScanning.getNumberOfBooks();
        int accumulate = 0;
        List<Library> output = new ArrayList<>();
        Set<Integer> stored = new HashSet<>();
        for (Library library : libraries) {
            if (accumulate < numberOfBooks && (library.getBooks().size() + accumulate < numberOfBooks)) {
                output.add(library);
                accumulate += library.getBooks().size();
                stored.addAll(library.getBooks().stream().map(Book::getScore).collect(Collectors.toSet()));
            } else {
                output.add(library);
                final List<Book> collect = library.getBooks().stream()
                        .filter(book -> {
                            for (Integer integer : stored) {
                                if (Integer.valueOf(book.getScore()).equals(integer)) {
                                    return false;
                                }
                            }
                            return true;
                        }).collect(Collectors.toList());
                if (numberOfBooks-accumulate <= collect.size()) {
                    library.addBooks(collect.subList(0, numberOfBooks-accumulate));
                }
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (int index = 0; index < output.size(); index++) {
            buffer.append(index+" "+output.get(index).getBooks().size()).append("\n");
            output.get(index).getBooks().forEach(book -> buffer.append(book.getScore()+" "));
            buffer.append("\n");
        }
        Files.write(Paths.get("src/test/output/" + fileName), buffer.toString().getBytes());
    }

    Comparator<Library> byScore = Comparator.comparing(Library::totalScore);
    Comparator<Library> bySignDuration = Comparator.comparing(Library::getDaysToTakeSign);
    Comparator<Library> byLimit = Comparator.comparing(Library::getLimitToShipBooksByDay);
}
