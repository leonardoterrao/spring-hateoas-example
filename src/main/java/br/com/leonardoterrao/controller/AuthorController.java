package br.com.leonardoterrao.controller;

import br.com.leonardoterrao.model.Author;
import br.com.leonardoterrao.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class AuthorController {

    @Autowired
    private LibraryService libraryService;

    @RequestMapping(value = "/author/{id}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseBody
    public Resource<Author> getAuthor(@PathVariable("id") Long id) {
        Author author = libraryService.getAuthor(id);
        Resource<Author> resource = new Resource<>(author);
        resource.add(linkTo(methodOn(AuthorController.class).getAuthor(id)).withSelfRel());
        return resource;
    }

}
