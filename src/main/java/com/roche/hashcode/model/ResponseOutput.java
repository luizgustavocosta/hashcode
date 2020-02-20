package com.roche.hashcode.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.util.List;

@Data
@RequiredArgsConstructor
@Builder
public class ResponseOutput {
    @Singular
    private final List<LibraryOutput> libraries;

    public Integer libraryAmount(){
        return libraries.size();
    }

    @Data
    @RequiredArgsConstructor
    @Builder
    public class LibraryOutput{
        private final int identifier;
        @Singular
        private final List<Book> books;

        public int[] getScannedBooks(){
            return books.stream().mapToInt(book->book.getIdentifier())
                    .toArray();
        }


    }
}
