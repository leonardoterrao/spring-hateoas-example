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
public class GreetingControllerTest {

    @Value("${local.server.port}")
    protected int port;

    @Test
    public void defaultValue() throws Exception {
        Traverson traverson = new Traverson(new URI("http://localhost:" + port + "/greeting"), MediaTypes.HAL_JSON);
        String greeting = traverson.follow("self").toObject("$.content");
        Assert.assertEquals("Hello, Person!", greeting);
    }

    @Test
    public void withParam() throws Exception {
        Traverson traverson = new Traverson(new URI("http://localhost:" + port + "/greeting?name=Leonardo"), MediaTypes.HAL_JSON);
        String greeting = traverson.follow("self").toObject("$.content");
        Assert.assertEquals("Hello, Leonardo!", greeting);
    }

}