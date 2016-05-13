package br.com.leonardoterrao.controller;

import br.com.leonardoterrao.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.URI;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
@DirtiesContext
public class AuthorControllerTest {

    @Value("${local.server.port}")
    protected int port;

    @Test
    public void find() throws Exception {
        Traverson traverson = new Traverson(new URI("http://localhost:" + port + "/author/1"), MediaTypes.HAL_JSON);

        final Integer id = traverson.follow("self").toObject("$.id");
        final String name = traverson.follow("self").toObject("$.name");

        Assert.assertEquals(Long.valueOf(1), Long.valueOf(id));
        Assert.assertEquals("Author X", name);
    }

}