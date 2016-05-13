package br.com.leonardoterrao.controller;

import br.com.leonardoterrao.Application;
import br.com.leonardoterrao.model.Author;
import br.com.leonardoterrao.model.Book;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
@DirtiesContext
public class BookControllerTest {

    @Value("${local.server.port}")
    protected int port;

    @Test
    public void getAllBooks() throws Exception {
        final ResponseEntity<Book[]> booksResponseEntity = new RestTemplate().getForEntity(new URI("http://localhost:" + port + "/books"), Book[].class);
        List<Book> books = Arrays.asList(booksResponseEntity.getBody());
        Assert.assertThat(4, Matchers.is(Matchers.equalTo(books.size())));
    }

    @Test
    public void getBook() throws Exception {
        final ResponseEntity<Book> bookResponseEntity = new RestTemplate().getForEntity(new URI("http://localhost:" + port + "/book/2"), Book.class);
        final Book book = bookResponseEntity.getBody();

        Assert.assertThat(Long.valueOf(2), Matchers.is(Matchers.equalTo(book.getId())));
        Assert.assertThat("Book Two", Matchers.is(Matchers.equalTo(book.getTitle())));
        Assert.assertThat(5, Matchers.is(Matchers.equalTo(book.getStockLevel())));
        Assert.assertThat("Author X", Matchers.is(Matchers.equalTo(book.getAuthor().getName())));
    }

    @Test
    public void borrowBook() throws Exception {
        String idBookToBorrow = "4";
        Traverson traverson = new Traverson(new URI("http://localhost:" + port + "/book/" + idBookToBorrow), MediaTypes.HAL_JSON);
        Integer stockLevel = traverson.follow("self").toObject("$.stockLevel");
        Assert.assertEquals(Integer.valueOf(9), stockLevel);

        traverson = new Traverson(new URI("http://localhost:" + port + "/book/borrow/" + idBookToBorrow), MediaTypes.HAL_JSON);
        stockLevel = traverson.follow("self").toObject("$.stockLevel");
        Assert.assertEquals(Integer.valueOf(8), stockLevel);

    }
}