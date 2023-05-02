package com.example.springbootproject.services;

import com.example.springbootproject.models.Book;
import com.example.springbootproject.models.Person;
import com.example.springbootproject.repositories.BooksRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final EntityManager entityManager;

    @Autowired
    public BooksService(BooksRepository booksRepository, EntityManager entityManager) {
        this.booksRepository = booksRepository;
        this.entityManager = entityManager;
    }
    public List<Book> index(){
        return booksRepository.findAll();
    }
    public List<Book> paginAndSort(int i, int j, boolean b){
        return b?booksRepository.findAll(PageRequest.of(i,j, Sort.by("yearOfPublishing"))).getContent()
                :booksRepository.findAll(PageRequest.of(i,j)).getContent();
    }
    public Book show(int id){
        return booksRepository.findById(id).orElse(null);
    }
    public Optional<Book> show(String name) {
        return booksRepository.findByName(name).stream().findAny();
    }
    public Optional<Book> show1(String name_of_creator) {
        return booksRepository.findByNameOfCreator(name_of_creator).stream().findAny();
    }
    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void update(int id,Book book){
        book.setBookId(id);
        booksRepository.save(book);
    }
    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }
    @Transactional
    public void deletePerson(int id){
        Session session = entityManager.unwrap(Session.class);
        Book book = session.get(Book.class, id);
        book.setCreatedAt(null);
        book.setOwner(null);
        booksRepository.save(book);
    }
    @Transactional
    public void addPerson(int id, Person person){
        Session session = entityManager.unwrap(Session.class);
        Book book = session.get(Book.class, id);
        book.setCreatedAt(new Date());
        book.setOwner(person);
        booksRepository.save(book);
    }

    public Person getPerson(int id){
        return booksRepository.findById(id).get().getOwner();
    }
    public Optional<Book> search(String nameStartingWith){
        return   booksRepository.findByNameStartingWith(nameStartingWith).stream().findAny();
    }
    public List <Book> hasProsrochka(List <Book> books){
        Date date = new Date();
        for (Book book: books){
            long elapsedms = date.getTime() - book.getCreatedAt().getTime();
            long diff = TimeUnit.DAYS.convert(elapsedms, TimeUnit.MILLISECONDS);
            book.setProsrochka(diff > 10);
        }
        return books;
    }
}
