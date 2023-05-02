package com.example.springbootproject.repositories;

import com.example.springbootproject.models.Book;
import com.example.springbootproject.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book,Integer> {
    List<Book> findByOwner(Person owner);
    List<Book> findByName(String bookName);
    List<Book> findByNameOfCreator(String nameOfCreator);
    List<Book> findByNameStartingWith(String name);

}
