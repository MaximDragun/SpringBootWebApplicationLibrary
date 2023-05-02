package com.example.springbootproject.util;

import com.example.springbootproject.models.Book;
import com.example.springbootproject.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    private final BooksService booksService;

@Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        if (booksService.show(book.getName()).isPresent()){
            errors.rejectValue("name","","this name already taken");
        }
        if (booksService.show1(book.getNameOfCreator()).isPresent()){
            errors.rejectValue("nameOfCreator","","this name_of_creator already taken");
        }
    }
}
