package br.com.leonardoterrao.service;

import br.com.leonardoterrao.model.Book;
import br.com.leonardoterrao.model.Author;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LibraryService {

    private final Map<Long, Book> books = new HashMap<>();
    private final Map<Long, Author> authors = new HashMap<>();;

    public Collection<Book> getAllBooks() {
        return books.values();
    }

    public Author getAuthor(final Long id) {
        return authors.get(id);
    }

    public Book getBook(final Long id) {
        return books.get(id);
    }

    public LibraryService() {
        populateLibrary();
    }

    private void populateLibrary() {
        populateAuthors();
        populateBooks();
    }

    private void populateAuthors() {
        Author authorX = new Author(1L, "Author X");
        Author authorY = new Author(2L, "Author Y");
        authors.put(authorX.getId(), authorX);
        authors.put(authorY.getId(), authorY);
    }

    private void populateBooks() {
        Book bookOne = new Book(1L, "Book One", authors.get(1L), 2);
        Book bookTwo = new Book(2L, "Book Two", authors.get(1L), 5);
        Book bookThree = new Book(3L, "Book Three", authors.get(2L), 3);
        Book bookFour = new Book(4L, "Book Four", authors.get(2L), 9);
        books.put(bookOne.getId(), bookOne);
        books.put(bookTwo.getId(), bookTwo);
        books.put(bookThree.getId(), bookThree);
        books.put(bookFour.getId(), bookFour);
    }

}
