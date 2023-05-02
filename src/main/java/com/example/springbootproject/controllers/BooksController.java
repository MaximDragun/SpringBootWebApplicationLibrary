package com.example.springbootproject.controllers;

import com.example.springbootproject.models.Book;
import com.example.springbootproject.models.Person;
import com.example.springbootproject.services.BooksService;
import com.example.springbootproject.services.PeopleService;
import com.example.springbootproject.util.BookValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;
    private final BookValidator bookValidator;


    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService, BookValidator bookValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(@RequestParam(value = "page", defaultValue = "0") Integer i,
                        @RequestParam(value = "books_per_page", defaultValue = "20") Integer j,
                        @RequestParam(value = "sort_by_year", defaultValue = "false") Boolean b, Model model) {
        model.addAttribute("books", booksService.paginAndSort(i, j, b));
//            model.addAttribute("books", booksService.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.show(id));
        if (booksService.show(id).getOwner() != null)
            model.addAttribute("person", booksService.getPerson(id));
        else model.addAttribute("person", new Person());
        model.addAttribute("people", peopleService.index());
        return "books/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        book.setBookId(id);
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/edit";
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/deletePerson")
    public String deletePerson(@PathVariable("id") int id) {
        booksService.deletePerson(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/addPerson")
    public String addPerson(@ModelAttribute("person") Person person, @PathVariable int id) {
        booksService.addPerson(id, person);
        return "redirect:/books";
    }
    @GetMapping("/search")
    public String search() {
        return "books/search";
    }

    @PostMapping ("/search")
    public String search_patch(@RequestParam(value = "name_search",required = false)  String name_search, Model model) {
      if (name_search.equals(""))
          return "books/search";
        model.addAttribute("name_search",name_search);
        if (booksService.search(name_search).isPresent()){
            Book b = booksService.search(name_search).get();
            model.addAttribute("book", b);
            if (b.getOwner() != null)
                model.addAttribute("person", booksService.getPerson(b.getBookId()));
        }
        return "books/search";
    }
}
