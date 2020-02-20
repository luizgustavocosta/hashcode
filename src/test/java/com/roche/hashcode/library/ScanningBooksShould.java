package com.roche.hashcode.library;

import com.roche.hashcode.model.Book;
import com.roche.hashcode.model.BookScanning;
import com.roche.hashcode.model.Library;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            final Library library = new Library(sorted,
                    Integer.parseInt(dataOfLibrary[1]),
                    Integer.parseInt(dataOfLibrary[2]));

            bookScanning.addLibrary(library);
        }
        return bookScanning;
    }

    @Test
    public void orderTheScanningToScenarioC() throws IOException {
        final BookScanning bookScanning = readInput("src/test/resources/c_incunabula.txt");
        final List<Library> libraries = bookScanning.getLibraries()
                .stream()
                .sorted(bySignDuration)
                .sorted(byScore.thenComparing(bySignDuration).reversed())
                .sorted(byLimit)
                .collect(Collectors.toList());

        int numberOfBooks = bookScanning.getNumberOfBooks();
        int accumalate = 0;
        List<Library> output = new ArrayList<>();
        Set<Integer> stored = new HashSet<>();
        for (Library library : libraries) {
            if (accumalate < numberOfBooks && (library.getBooks().size() + accumalate < numberOfBooks)) {
                output.add(library);
                accumalate += library.getBooks().size();
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
                if (numberOfBooks-accumalate <= collect.size()) {
                    library.addBooks(collect.subList(0, numberOfBooks-accumalate));
                }
            }
        }

        System.out.println(output.size());

        for (int index = 0; index < output.size(); index++) {
            System.out.println(index+" "+output.get(index).getBooks().size());
            output.get(index).getBooks().forEach(book -> System.out.print(book.getScore()+" "));
            System.out.println("");
        }
        /*System.out.println("");
        System.out.println("1"+" "+output.get(1).getBooks().size());
        output.get(1).getBooks().forEach(book -> System.out.print(book.getScore()+" "));*/
    }

    Comparator<Library> byScore = Comparator.comparing(Library::totalScore);
    Comparator<Library> bySignDuration = Comparator.comparing(Library::getDaysToTakeSign);
    Comparator<Library> byLimit = Comparator.comparing(Library::getLimitToShipBooksByDay);
}
