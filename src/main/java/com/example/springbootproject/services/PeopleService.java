package com.example.springbootproject.services;

import com.example.springbootproject.models.Book;
import com.example.springbootproject.models.Person;
import com.example.springbootproject.repositories.BooksRepository;
import com.example.springbootproject.repositories.PeopleRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final EntityManager entityManager;
    private final BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, EntityManager entityManager, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.entityManager = entityManager;
        this.booksRepository = booksRepository;
    }
    public List<Person> index(){
        return peopleRepository.findAll();
    }
    public Person show(int id){
        return peopleRepository.findById(id).orElse(null);
    }
    public Optional<Person> show(String fio) {
        return peopleRepository.findByFio(fio).stream().findAny();
    }
    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id,Person person){
        person.setPersonId(id);
        peopleRepository.save(person);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public List<Book> listBooks(int id) {
        Session session = entityManager.unwrap(Session.class);
        Person person = session.get(Person.class, id);
        return booksRepository.findByOwner(person);
    }
}
