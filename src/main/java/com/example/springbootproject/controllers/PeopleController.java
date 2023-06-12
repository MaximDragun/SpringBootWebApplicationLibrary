package com.example.springbootproject.controllers;

import com.example.springbootproject.models.Person;
import com.example.springbootproject.services.BooksService;
import com.example.springbootproject.services.PeopleService;
import com.example.springbootproject.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final PersonValidator pErsonValidator;
    private final BooksService booksService;


    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator pErsonValidator, BooksService booksService) {
        this.peopleService = peopleService;
        this.pErsonValidator = pErsonValidator;
        this.booksService = booksService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {

        model.addAttribute("person", peopleService.show(id));
        model.addAttribute("books",booksService.hasProsrochka(peopleService.listBooks(id)));

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        pErsonValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        person.setPersonId(id);
        pErsonValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
