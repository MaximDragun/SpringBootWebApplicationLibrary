package com.example.springbootproject.util;

import com.example.springbootproject.models.Person;
import com.example.springbootproject.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;


    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }



    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (peopleService.show(person.getFio()).isPresent()){
            errors.rejectValue("fio","","this fio already taken");
        }
    }
}
