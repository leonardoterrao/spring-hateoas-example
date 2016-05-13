package br.com.leonardoterrao.controller;

import br.com.leonardoterrao.model.Book;
import br.com.leonardoterrao.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class BookController {

    @Autowired
    private LibraryService libraryService;

    @RequestMapping(value = "/books", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseBody
    public Collection<Resource<Book>> getAllBooks() {
        return libraryService.getAllBooks()
                .stream()
                .map(book -> getAuthorResource(book))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseBody
    public Resource<Book> getBook(@PathVariable("id") Long id) {
        Book book = libraryService.getBook(id);
        return getAuthorResource(book);
    }


    private Resource<Book> getAuthorResource(Book book) {
        Resource<Book> bookResource = new Resource<>(book);
        bookResource.add(linkTo(methodOn(BookController.class).getBook(book.getId())).withSelfRel());
        bookResource.add(linkTo(methodOn(AuthorController.class).getAuthor(book.getAuthor().getId())).withSelfRel());

        if (book.getStockLevel() > 0) {
            bookResource.add(linkTo(methodOn(BookController.class).borrowBook(book.getId())).withRel("book.borrow"));
        }

        return bookResource;
    }

    @RequestMapping(value = "/book/borrow/{id}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public Resource<Book> borrowBook(@PathVariable("id") Long id) {
        Book book = libraryService.getBook(id);
        book.borrowOne();
        Resource<Book> bookResource = new Resource<>(book);
        bookResource.add(linkTo(methodOn(BookController.class).getBook(id)).withSelfRel());
        return bookResource;
    }
}
